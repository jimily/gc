package cn.gc.service.adapter.model.input;

/**
 * @author wangjingru
 * @description 购买版权
 * @email wangjingru@bubi.cn
 * @creatTime 2018/6/9 20:13
 * @since 1.0.0
 */
public class BuyCopyRightInput {
    /**
     * 操作类型："saleCopyright"
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
     * 创意合约地址
     */
    private String contractAddress;
    /**
     * 购买金额：单位分
     */
    private Long amount;
    /**
     * 创意登记人地址
     */
    private String registrant;

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

    public String getContractAddress() {
        return contractAddress;
    }

    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getRegistrant() {
        return registrant;
    }

    public void setRegistrant(String registrant) {
        this.registrant = registrant;
    }
}
