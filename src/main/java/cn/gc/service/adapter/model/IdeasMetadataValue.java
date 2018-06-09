package cn.gc.service.adapter.model;

/**
 * @author wangjingru
 * @description TODO
 * @email wangjingru@bubi.cn
 * @creatTime 2018/6/9 22:49
 * @since 1.0.0
 */
public class IdeasMetadataValue {
    private String status;
    private Long income;
    private CopyrightInfo publication;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getIncome() {
        return income;
    }

    public void setIncome(Long income) {
        this.income = income;
    }

    public CopyrightInfo getPublication() {
        return publication;
    }

    public void setPublication(CopyrightInfo publication) {
        this.publication = publication;
    }
}
