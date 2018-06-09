package cn.gc.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A ProjectSchedule.
 */
@Entity
@Table(name = "biz_project_schedule")
public class ProjectSchedule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @NotNull
    @Column(name = "project_id", nullable = false)
    private Long projectId;

    @NotNull
    @Column(name = "balance", nullable = false)
    private Long balance;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "tx_hash", length = 255, nullable = false)
    private String txHash;

    @NotNull
    @Column(name = "create_time", nullable = false)
    private Long createTime;

    @NotNull
    @Column(name = "update_time", nullable = false)
    private Long updateTime;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public ProjectSchedule userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public ProjectSchedule projectId(Long projectId) {
        this.projectId = projectId;
        return this;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getBalance() {
        return balance;
    }

    public ProjectSchedule balance(Long balance) {
        this.balance = balance;
        return this;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public String getTxHash() {
        return txHash;
    }

    public ProjectSchedule txHash(String txHash) {
        this.txHash = txHash;
        return this;
    }

    public void setTxHash(String txHash) {
        this.txHash = txHash;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public ProjectSchedule createTime(Long createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public ProjectSchedule updateTime(Long updateTime) {
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
        ProjectSchedule projectSchedule = (ProjectSchedule) o;
        if (projectSchedule.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), projectSchedule.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProjectSchedule{" +
            "id=" + getId() +
            ", userId=" + getUserId() +
            ", projectId=" + getProjectId() +
            ", balance=" + getBalance() +
            ", txHash='" + getTxHash() + "'" +
            ", createTime=" + getCreateTime() +
            ", updateTime=" + getUpdateTime() +
            "}";
    }
}
