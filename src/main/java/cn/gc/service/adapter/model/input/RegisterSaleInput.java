package cn.gc.service.adapter.model.input;

/**
 * @author wangjingru
 * @description 登记版权售卖
 * @email wangjingru@bubi.cn
 * @creatTime 2018/6/9 20:09
 * @since 1.0.0
 */
public class RegisterSaleInput {
    /**
     * 操作类型："registerSale"
     */
    private String action;
    /**
     * 版权类型：'publication'
     */
    private String copyright;
    /**
     * 项目：'ideas-类型-名称-创意合约地址'
     */
    private String project;
    /**
     * 贩卖金额：单位分
     */
    private Long amount;
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

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getContractAddress() {
        return contractAddress;
    }

    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }
}
