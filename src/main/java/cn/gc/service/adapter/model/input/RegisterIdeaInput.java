package cn.gc.service.adapter.model.input;

/**
 * @author wangjingru
 * @description 创意登记信息
 * @email wangjingru@bubi.cn
 * @creatTime 2018/6/9 17:11
 * @since 1.0.0
 */
public class RegisterIdeaInput {
    /**
     * 操作类型："create"
     */
    private String action;
    /**
     * 项目名称
     */
    private String name;
    /**
     * 合约类型，idea
     */
    private String type;
    /**
     * 创意类别
     */
    private String classify;
    /**
     * 众筹占比
     */
    private Double rate;
    /**
     * 总成本
     */
    private Long totalCost;
    /**
     * 单次购买下限
     */
    private Long lowestLimit;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Long getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Long totalCost) {
        this.totalCost = totalCost;
    }

    public Long getLowestLimit() {
        return lowestLimit;
    }

    public void setLowestLimit(Long lowestLimit) {
        this.lowestLimit = lowestLimit;
    }
}
