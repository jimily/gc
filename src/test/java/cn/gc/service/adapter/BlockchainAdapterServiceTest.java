package cn.gc.service.adapter;

import cn.bubi.access.starter.BCAutoConfiguration;
import cn.bubi.access.starter.BCAutoConfigurationSpi;
import cn.bubi.sdk.core.seq.redis.RedisClient;
import cn.gc.GcApp;
import cn.gc.service.adapter.constant.ContractConstant;
import cn.gc.service.adapter.model.input.FinishContractInput;
import cn.gc.service.adapter.model.input.InvestIdeaInput;
import cn.gc.service.adapter.model.input.RegisterIdeaInput;
import cn.gc.service.adapter.model.input.RegisterSaleInput;
import cn.gc.service.adapter.model.output.BlockchainAccount;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author wangjingru
 * @description TODO
 * @email wangjingru@bubi.cn
 * @creatTime 2018/6/9 21:01
 * @since 1.0.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {GcApp.class,BCAutoConfiguration.class, BCAutoConfigurationSpi.class})
public class BlockchainAdapterServiceTest {
    @Autowired
    private BlockchainAdapterService blockchainAdapterService;
    @MockBean
    private RedisClient redisClient;

    private static String projectClassify = "book";
    private static String projectName = "我想写本小说";
    private static String projectType = "ideas";
    private static String contractAddress;
    private static BlockchainAccount registant;
    private static BlockchainAccount investor;

    /**
     * 注册账号
     */
    @Test
    public void registerBlockchainAccountTest() {
        BlockchainAccount account = blockchainAdapterService.registerBlockchainAccount();
        System.out.println(account.toString());
    }

    /**
     * 注册登记人并登记创意
     */
    @Test
    public void registerIdeaTest() {
        registant = blockchainAdapterService.registerBlockchainAccount();
        System.out.println(registant.toString());
        RegisterIdeaInput input = new RegisterIdeaInput();
        input.setAction(ContractConstant.CREATE);
        input.setClassify(projectClassify);
        input.setLowestLimit(0L);
        input.setName(projectName);
        input.setRate((double)60);
        input.setTotalCost(20000000L);
        input.setType(projectType);
        contractAddress = blockchainAdapterService.registerIdea(registant, input);
        System.out.println(contractAddress);
    }

    /**
     * 注册登记人、登记创意、注册投资人、投资创意
     */
    @Test
    public void investIdeaTest() {
        registerIdeaTest();
        investor = blockchainAdapterService.registerBlockchainAccount();
        System.out.println(investor.toString());
        InvestIdeaInput investIdeaInput = new InvestIdeaInput();
        investIdeaInput.setAction(ContractConstant.INVEST);
        investIdeaInput.setAmount(1200000L);
        investIdeaInput.setContractAddress(contractAddress);
        investIdeaInput.setProject(getIdeaMetadataKey("invest", projectClassify, projectName, contractAddress));
        blockchainAdapterService.investIdea(investor, investIdeaInput);
    }
    /**
     * 注册登记人、登记创意、注册投资人、投资创意、手动结束众筹
     */
    @Test
    public void finishIdeaTest() {
        investIdeaTest();
        FinishContractInput finishContractInput = new FinishContractInput();
        finishContractInput.setAction(ContractConstant.FINISH);
        finishContractInput.setContractAddress(contractAddress);
        blockchainAdapterService.finishIdea(registant, finishContractInput);
    }

    /**
     * 登记版权售卖
     */
    @Test
    public void registerSaleTest() {
        BlockchainAccount sponsor = new BlockchainAccount();
        sponsor.setAddress("a00111117d938e836117d6b3c8f57302ecc719ed10bc6d");
        sponsor.setPrivateKey("c001b53ef341ffe604979a26457b682fed92e439a5e7e48967b6b66b7f91ae7917a2a4");
        sponsor.setPublicKey("b001b8a1960f4d1411e4ebc6949b0da6d8bacb2104aefb097b9cf47194f0b2e21af723");

        String contractAddress = "a001a4074b2f2a671ad83a9ba58612be5338295ef26c8f";

        RegisterSaleInput registerSaleInput = new RegisterSaleInput();
        registerSaleInput.setAction(ContractConstant.REGISTERSALE);
        registerSaleInput.setAmount(23400000L);
        registerSaleInput.setCopyright("publication");
        registerSaleInput.setProject(getIdeaMetadataKey("ideas", projectClassify, projectName, contractAddress));
        registerSaleInput.setContractAddress(contractAddress);
        blockchainAdapterService.registerSale(sponsor, registerSaleInput);
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
}
