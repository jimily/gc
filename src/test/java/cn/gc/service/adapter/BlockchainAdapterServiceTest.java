package cn.gc.service.adapter;

import cn.bubi.access.starter.BCAutoConfiguration;
import cn.bubi.access.starter.BCAutoConfigurationSpi;
import cn.bubi.sdk.core.exception.SdkException;
import cn.bubi.sdk.core.operation.OperationFactory;
import cn.bubi.sdk.core.operation.impl.InvokeContractOperation;
import cn.bubi.sdk.core.seq.redis.RedisClient;
import cn.bubi.sdk.core.spi.BcOperationService;
import cn.gc.GcApp;
import cn.gc.service.adapter.constant.ContractConstant;
import cn.gc.service.adapter.model.input.*;
import cn.gc.service.adapter.model.output.BlockchainAccount;
import com.alibaba.fastjson.JSONObject;
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
    @Autowired
    private BcOperationService operationService;

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
        sponsor.setAddress("a001522e091c25280bda0ef990364aca51f42a4f54b23d");
        sponsor.setPrivateKey("c001bedb660483f55217982dca48df18fa28dbe1ea1ded5ef0c2703b9df23b33010e11");
        sponsor.setPublicKey("b001d555d9b555ac657813964d53026b76e13d0adf5b44d44860bbc1ff152c735fd091");

        String contractAddress = "a00167482d80e54c9f5a25d6a31af4b6881665d51a27b6";

        RegisterSaleInput registerSaleInput = new RegisterSaleInput();
        registerSaleInput.setAction(ContractConstant.REGISTERSALE);
        registerSaleInput.setAmount(23400000L);
        registerSaleInput.setCopyright("publication");
        registerSaleInput.setProject(getIdeaMetadataKey("ideas", projectClassify, projectName, contractAddress));
        registerSaleInput.setContractAddress(contractAddress);
        blockchainAdapterService.registerSale(sponsor, registerSaleInput);
    }

    /**
     * 购买版权
     */
    @Test
    public void buyCopyrightTest() {
        BlockchainAccount sponsor = blockchainAdapterService.registerBlockchainAccount();
        System.out.println(sponsor.toString());

        String contractAddress = "a00167482d80e54c9f5a25d6a31af4b6881665d51a27b6";
        BuyCopyRightInput buyCopyRightInput = new BuyCopyRightInput();
        buyCopyRightInput.setAction(ContractConstant.SALECOPYRIGHT);
        buyCopyRightInput.setAmount(23400000L);
        buyCopyRightInput.setContractAddress(contractAddress);
        buyCopyRightInput.setCopyright("publication");
        buyCopyRightInput.setRegistrant("a001522e091c25280bda0ef990364aca51f42a4f54b23d");
        buyCopyRightInput.setProject(getIdeaMetadataKey("ideas", projectClassify, projectName, contractAddress));
        blockchainAdapterService.buyCopyright(sponsor, buyCopyRightInput);
    }

    /*@Test
    public void profitTest(){

        BlockchainAccount sponsor = new BlockchainAccount();
        sponsor.setAddress("a001a5c870eb28fba263ffca26afec9709b06c7bfbdac8");
        sponsor.setPrivateKey("c001e60131499408cd074ec4b3ce277be3fdc0451421e54e7ae493a1a8364fee9044a6");
        sponsor.setPublicKey("b0015152b21c910f5e9c70e17deeec99925c1ea9a144c6a60922dec7644eeab165c5be");

        String contractAddress = "a001af45b6b7b4f529cdc39e5888036d872c6d6f71a4ab";
        BuyCopyRightInput buyCopyRightInput = new BuyCopyRightInput();
        buyCopyRightInput.setAction("incomeOfIdea");
        buyCopyRightInput.setAmount(21996000L);
        buyCopyRightInput.setProject("ideas-book-我想写本小说-a001a5c870eb28fba263ffca26afec9709b06c7bfbdac8");
        buyCopyRightInput.setContractAddress("a001a5c870eb28fba263ffca26afec9709b06c7bfbdac8");
        try {
            InvokeContractOperation invokeContractOperation = new InvokeContractOperation.Builder()
                .buildDestAddress(contractAddress)
                .buildInputData(JSONObject.toJSONString(buyCopyRightInput))
                .build();
            operationService.newTransaction(sponsor.getAddress())
                .buildAddOperation(invokeContractOperation)
                .buildAddSigner(sponsor.getPublicKey(), sponsor.getPrivateKey())
                .commit();
        } catch (SdkException e) {
            System.err.println(e.getErrorMessage());
        }
    }*/

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
