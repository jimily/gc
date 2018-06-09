package cn.gc.service.adapter;

import cn.bubi.access.adaptation.blockchain.bc.response.operation.SetMetadata;
import cn.bubi.access.utils.blockchain.BlockchainKeyPair;
import cn.bubi.access.utils.blockchain.SecureKeyGenerator;
import cn.bubi.sdk.core.exception.SdkException;
import cn.bubi.sdk.core.operation.OperationFactory;
import cn.bubi.sdk.core.operation.impl.CreateAccountOperation;
import cn.bubi.sdk.core.operation.impl.InvokeContractOperation;
import cn.bubi.sdk.core.spi.BcOperationService;
import cn.bubi.sdk.core.spi.BcQueryService;
import cn.gc.config.BlockchainConfig;
import cn.gc.service.adapter.constant.ContractStatus;
import cn.gc.service.adapter.constant.SaleStatusConstant;
import cn.gc.service.adapter.model.CopyrightInfo;
import cn.gc.service.adapter.model.IdeasMetadataValue;
import cn.gc.service.adapter.model.InvestMetadataValue;
import cn.gc.service.adapter.model.input.*;
import cn.gc.service.adapter.model.output.BlockchainAccount;
import cn.gc.service.adapter.model.output.IdeasMetadataValueTemp;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStreamReader;
import java.net.URL;

/**
 * @author wangjingru
 * @description 区块链调用实现
 * @email wangjingru@bubi.cn
 * @creatTime 2018/6/9 20:28
 * @since 1.0.0
 */
@Service
public class BlockchainAdapterService {
    private final Logger logger = LoggerFactory.getLogger(BlockchainAdapterService.class);
    @Autowired
    private BcOperationService operationService;
    @Autowired
    private BlockchainConfig blockchainConfig;
    @Autowired
    private BcQueryService bcQueryService;
    /**
     * 注册区块链账户
     * @return  区块链账户信息
     */
    public BlockchainAccount registerBlockchainAccount() {
        BlockchainKeyPair destAccount = SecureKeyGenerator.generateBubiKeyPair();
        try {
            CreateAccountOperation createAccountOperation = new CreateAccountOperation.Builder()
                .buildDestAddress(destAccount.getBubiAddress())
                // 权限部分
                .buildPriMasterWeight(10)
                .buildPriTxThreshold(10)
                .buildScript(getContract4User())
                .buildAddMetadata("outlay", "0")
                .buildAddMetadata("income", "0")
                .build();
            operationService.newTransaction(blockchainConfig.getAddress())
                .buildAddOperation(createAccountOperation)
                .buildAddSigner(blockchainConfig.getPubKey(), blockchainConfig.getPriKey())
                .commit();
        } catch (SdkException e) {
            logger.error("创建区块链账户失败", e);
        }
        BlockchainAccount account = new BlockchainAccount();
        account.setAddress(destAccount.getBubiAddress());
        account.setPrivateKey(destAccount.getPriKey());
        account.setPublicKey(destAccount.getPubKey());
        return account;
    }

    /**
     * 登记创意众筹
     * @param sponsor   创意登记人
     * @param input
     * @return  返回创意众筹对应的合约地址
     */
    public String registerIdea(BlockchainAccount sponsor, RegisterIdeaInput input) {
        BlockchainKeyPair destAccount = SecureKeyGenerator.generateBubiKeyPair();
        System.out.println(destAccount.toString());
        String inputData = JSONObject.toJSONString(input);
        String contractContent = getContract4Idea();
        contractContent = contractContent.replace("{temp_registrant}", sponsor.getAddress());
        try {
            CreateAccountOperation createAccountOperation = new CreateAccountOperation.Builder()
                .buildDestAddress(destAccount.getBubiAddress())
                // 权限部分
                .buildPriMasterWeight(10)
                .buildPriTxThreshold(10)
                .buildOperationSourceAddress(sponsor.getAddress())
                .buildScript(contractContent)
                .build();
            InvokeContractOperation invokeContractOperation = new InvokeContractOperation.Builder()
                .buildDestAddress(destAccount.getBubiAddress())
                .buildInputData(inputData)
                .build();
            String key = getIdeaMetadataKey(
                "ideas", input.getClassify(), input.getName(), destAccount.getBubiAddress());
            operationService.newTransaction(sponsor.getAddress())
                .buildAddOperation(createAccountOperation)
                .buildAddOperation(invokeContractOperation)
                .buildAddOperation(OperationFactory.newSetMetadataOperation(key, JSONObject.toJSONString(initIdeasMetadataValue(destAccount.getBubiAddress()))))
                .buildAddSigner(sponsor.getPublicKey(), sponsor.getPrivateKey())
                .commit();
        } catch (SdkException e) {
            logger.error("创建区块链账户失败", e);
        }
        return destAccount.getBubiAddress();
    }

    /**
     * 投资创意
     * @param sponsor   投资人
     * @param input
     */
    public void investIdea(BlockchainAccount sponsor, InvestIdeaInput input) {
        SetMetadata projectMetadata = bcQueryService.getAccount(sponsor.getAddress(), input.getProject());
        SetMetadata outlayMetadata = bcQueryService.getAccount(sponsor.getAddress(), "outlay");
        Long totalCost = input.getAmount();
        InvestMetadataValue investHistory = new InvestMetadataValue();
        if (projectMetadata != null) {
            investHistory = (InvestMetadataValue)JSON.parse(projectMetadata.getValue());
            investHistory.setOutlay(investHistory.getOutlay() + totalCost);
        } else {
            investHistory.setContractAddress(input.getContractAddress());
            investHistory.setIncome(0L);
            investHistory.setOutlay(totalCost);
        }
        Long outlayOfHistory =input.getAmount();
        if (outlayMetadata != null) {
            outlayOfHistory = (Integer)JSON.parse(outlayMetadata.getValue()) + outlayOfHistory;
        }
        String inputData = JSONObject.toJSONString(input);
        try {
            InvokeContractOperation invokeContractOperation = new InvokeContractOperation.Builder()
                .buildDestAddress(input.getContractAddress())
                .buildInputData(inputData)
                .build();
            operationService.newTransaction(sponsor.getAddress())
                .buildAddOperation(
                    OperationFactory.newSetMetadataOperation(
                        input.getProject(), JSONObject.toJSONString(investHistory)))
                .buildAddOperation(
                    OperationFactory.newSetMetadataOperation(
                        "outlay", outlayOfHistory.toString()
                    )
                )
                .buildAddOperation(invokeContractOperation)
                .buildAddSigner(sponsor.getPublicKey(), sponsor.getPrivateKey())
                .commit();
        } catch (SdkException e) {
            logger.error("调用投资创意合约失败", e);
        }
    }

    /**
     * 手动结束众筹
     * @param sponsor   创意登记人
     * @param input
     */
    public void finishIdea(BlockchainAccount sponsor, FinishContractInput input) {
        String inputData = JSONObject.toJSONString(input);
        try {
            InvokeContractOperation invokeContractOperation = new InvokeContractOperation.Builder()
                .buildDestAddress(input.getContractAddress())
                .buildInputData(inputData)
                .build();
            operationService.newTransaction(sponsor.getAddress())
                .buildAddOperation(invokeContractOperation)
                .buildAddSigner(sponsor.getPublicKey(), sponsor.getPrivateKey())
                .commit();
        } catch (SdkException e) {
            logger.error("调用投资创意合约失败", e);
        }
    }

    /**
     * 贩卖版权
     * @param sponsor   创意登记人
     * @param input
     */
    public void registerSale(BlockchainAccount sponsor, RegisterSaleInput input) {
        SetMetadata projectMetadata = bcQueryService.getAccount(sponsor.getAddress(), input.getProject());
        JSONObject projectJSONObject = JSONObject.parseObject(projectMetadata.getValue());
        JSONObject publicationJSONObject = JSONObject.parseObject(projectJSONObject.get("publication").toString());
        //TODO 逻辑校验
        CopyrightInfo publication = new CopyrightInfo();
        publication.setStatus(SaleStatusConstant.SALEING);
        publication.setAmount(input.getAmount());

        IdeasMetadataValue ideasMetadataValue = new IdeasMetadataValue();
        ideasMetadataValue.setStatus(projectJSONObject.get("status").toString());
        ideasMetadataValue.setIncome(Long.parseLong(projectJSONObject.get("income").toString()));
        ideasMetadataValue.setPublication(publication);
        ideasMetadataValue.setContractAddress(input.getContractAddress());
        try {
            operationService.newTransaction(sponsor.getAddress())
                .buildAddOperation(OperationFactory.newSetMetadataOperation(input.getProject(), JSONObject.toJSONString(ideasMetadataValue)))
                .buildAddSigner(sponsor.getPublicKey(), sponsor.getPrivateKey())
                .commit();
        } catch (SdkException e) {
            logger.error("调用贩卖版权合约失败", e);
        }
    }

    /**
     * 购买版权
     * @param sponsor   投资人
     * @param input
     */
    public void buyCopyright(BlockchainAccount sponsor, BuyCopyRightInput input) {
        SetMetadata sponsorOutlay = bcQueryService.getAccount(sponsor.getAddress(), "outlay");
        Integer outlay = (Integer)JSONObject.parse(sponsorOutlay.getValue());
        outlay = outlay + input.getAmount().intValue();
        String inputData = JSONObject.toJSONString(input);
        try {
            InvokeContractOperation invokeContractOperation = new InvokeContractOperation.Builder()
                .buildDestAddress(input.getRegistrant())
                .buildInputData(inputData)
                .build();
            operationService.newTransaction(sponsor.getAddress())
                .buildAddOperation(invokeContractOperation)
                .buildAddOperation(OperationFactory.newSetMetadataOperation("outlay", outlay.toString()))
                .buildAddSigner(sponsor.getPublicKey(), sponsor.getPrivateKey())
                .commit();
        } catch (SdkException e) {
            logger.error("调用购买版权合约失败", e);
        }
    }

    private String getContract4User() {
        try {
            URL url = getClass().getClassLoader().getResource("template/user.js");

            InputStreamReader inputReader = new InputStreamReader(url.openStream());

            return IOUtils.toString(inputReader);

        } catch (Exception e) {
            logger.error("load template loader failed. template type: user", e);
        }
        return null;
    }

    private String getContract4Idea() {
        try {
            URL url = getClass().getClassLoader().getResource("template/idea.js");

            InputStreamReader inputReader = new InputStreamReader(url.openStream());

            return IOUtils.toString(inputReader);

        } catch (Exception e) {
            logger.error("load template loader failed. template type: idea", e);
        }
        return null;
    }

    private String getIdeaMetadataKey(String type, String classify, String name , String contractAddress) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(type)
            .append("-")
            .append(classify)
            .append("-")
            .append(name)
            .append("-")
            .append(contractAddress);
        return stringBuffer.toString();
    }

    private IdeasMetadataValue initIdeasMetadataValue(String contractAddress) {
        CopyrightInfo copyrightInfo = new CopyrightInfo();
        copyrightInfo.setStatus(SaleStatusConstant.NOTFORSALE);
        copyrightInfo.setBuyer("");
        copyrightInfo.setAmount(0L);
        IdeasMetadataValue ideasMetadataValue = new IdeasMetadataValue();
        ideasMetadataValue.setStatus(ContractStatus.CROWDFUNDING);
        ideasMetadataValue.setIncome(0L);
        ideasMetadataValue.setPublication(copyrightInfo);
        ideasMetadataValue.setContractAddress(contractAddress);
        return ideasMetadataValue;
    }

}
