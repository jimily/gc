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
     * 版权类型：'PUBLICATION'
     */
    private String copyRight;
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

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getCopyRight() {
        return copyRight;
    }

    public void setCopyRight(String copyRight) {
        this.copyRight = copyRight;
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
}
