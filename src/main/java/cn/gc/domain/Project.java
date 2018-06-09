package cn.gc.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Project.
 */
@Entity
@Table(name = "biz_project")
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @NotNull
    @Column(name = "jhi_type", nullable = false)
    private Integer type;

    @NotNull
    @Size(min = 1, max = 1024)
    @Column(name = "description", length = 1024, nullable = false)
    private String description;

    @Column(name = "copyright_no")
    private String copyrightNo;

    @NotNull
    @Column(name = "ratio", nullable = false)
    private Integer ratio;

    @NotNull
    @Column(name = "gross", nullable = false)
    private Long gross;

    @NotNull
    @Column(name = "once_buy_limit", nullable = false)
    private Long onceBuyLimit;

    @NotNull
    @Column(name = "balance", nullable = false)
    private Long balance;

    @NotNull
    @Column(name = "is_end", nullable = false)
    private Integer isEnd;


    @Column(name = "contract_address", length = 64, nullable = false)
    private String contractAddress;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "create_time", nullable = false)
    private Long createTime;

    @Column(name = "update_time", nullable = false)
    private Long updateTime;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Project name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public Project type(Integer type) {
        this.type = type;
        return this;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public Project description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCopyrightNo() {
        return copyrightNo;
    }

    public Project copyrightNo(String copyrightNo) {
        this.copyrightNo = copyrightNo;
        return this;
    }

    public void setCopyrightNo(String copyrightNo) {
        this.copyrightNo = copyrightNo;
    }

    public Integer getRatio() {
        return ratio;
    }

    public Project ratio(Integer ratio) {
        this.ratio = ratio;
        return this;
    }

    public void setRatio(Integer ratio) {
        this.ratio = ratio;
    }

    public Long getGross() {
        return gross;
    }

    public Project gross(Long gross) {
        this.gross = gross;
        return this;
    }

    public void setGross(Long gross) {
        this.gross = gross;
    }

    public Long getOnceBuyLimit() {
        return onceBuyLimit;
    }

    public Project onceBuyLimit(Long onceBuyLimit) {
        this.onceBuyLimit = onceBuyLimit;
        return this;
    }

    public void setOnceBuyLimit(Long onceBuyLimit) {
        this.onceBuyLimit = onceBuyLimit;
    }

    public Long getBalance() {
        return balance;
    }

    public Project balance(Long balance) {
        this.balance = balance;
        return this;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public Integer getIsEnd() {
        return isEnd;
    }

    public Project isEnd(Integer isEnd) {
        this.isEnd = isEnd;
        return this;
    }

    public void setIsEnd(Integer isEnd) {
        this.isEnd = isEnd;
    }

    public String getContractAddress() {
        return contractAddress;
    }

    public Project contractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
        return this;
    }

    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }

    public Long getUserId() {
        return userId;
    }

    public Project userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public Project createTime(Long createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public Project updateTime(Long updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Project project = (Project) o;
        if (project.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), project.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Project{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type=" + getType() +
            ", description='" + getDescription() + "'" +
            ", copyrightNo='" + getCopyrightNo() + "'" +
            ", ratio=" + getRatio() +
            ", gross=" + getGross() +
            ", onceBuyLimit=" + getOnceBuyLimit() +
            ", balance=" + getBalance() +
            ", isEnd=" + getIsEnd() +
            ", contractAddress='" + getContractAddress() + "'" +
            ", userId=" + getUserId() +
            ", createTime=" + getCreateTime() +
            ", updateTime=" + getUpdateTime() +
            "}";
    }
}
