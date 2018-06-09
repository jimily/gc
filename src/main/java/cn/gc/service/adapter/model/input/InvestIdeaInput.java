package cn.gc.service.adapter.model.input;

/**
 * @author wangjingru
 * @description 投资创意入参
 * @email wangjingru@bubi.cn
 * @creatTime 2018/6/9 19:55
 * @since 1.0.0
 */
public class InvestIdeaInput {
    /**
     * 操作类型："invest"
     */
    private String action;
    /**
     * 创意合约地址
     */
    private String contractAddress;
    /**
     * 投资金额
     */
    private Long amount;
    /**
     * 项目名：'invest-类型-名称-创意合约地址'
     */
    private String project;

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

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }
}
