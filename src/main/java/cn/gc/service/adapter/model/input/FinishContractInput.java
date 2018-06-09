package cn.gc.service.adapter.model.input;

/**
 * @author wangjingru
 * @description 手动结束众筹入参
 * @email wangjingru@bubi.cn
 * @creatTime 2018/6/9 20:05
 * @since 1.0.0
 */
public class FinishContractInput {
    /**
     * 操作类型："finish"
     */
    private String action;
    /**
     * 合约地址
     */
    private String contractAddress;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getContractAddress() {
        return contractAddress;
    }

    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }
}
