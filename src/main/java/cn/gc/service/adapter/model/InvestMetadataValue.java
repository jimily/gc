package cn.gc.service.adapter.model;

/**
 * @author wangjingru
 * @description TODO
 * @email wangjingru@bubi.cn
 * @creatTime 2018/6/9 23:54
 * @since 1.0.0
 */
public class InvestMetadataValue {
    private String contractAddress;
    private Long income;
    private Long outlay;

    public String getContractAddress() {
        return contractAddress;
    }

    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }

    public Long getIncome() {
        return income;
    }

    public void setIncome(Long income) {
        this.income = income;
    }

    public Long getOutlay() {
        return outlay;
    }

    public void setOutlay(Long outlay) {
        this.outlay = outlay;
    }
}
