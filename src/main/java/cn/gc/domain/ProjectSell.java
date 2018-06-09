package cn.gc.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A ProjectSell.
 */
@Entity
@Table(name = "biz_project_sell")
public class ProjectSell implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "project_id", nullable = false)
    private Long projectId;

    @NotNull
    @Column(name = "copyright_sell_type", nullable = false)
    private Integer copyrightSellType;

    @NotNull
    @Column(name = "selling_price", nullable = false)
    private Long sellingPrice;


    @Column(name = "tx_hash", length = 255, nullable = false)
    private String txHash;

    @NotNull
    @Size(min = 1, max = 1024)
    @Column(name = "description", length = 1024, nullable = false)
    private String description;

    @Column(name = "owner_user_id", nullable = false)
    private Long ownerUserId;

    @Column(name = "buyer_id", nullable = false)
    private Long buyerId;

    @Column(name = "is_sell", nullable = false)
    private Integer isSell;

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

    public Long getProjectId() {
        return projectId;
    }

    public ProjectSell projectId(Long projectId) {
        this.projectId = projectId;
        return this;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Integer getCopyrightSellType() {
        return copyrightSellType;
    }

    public ProjectSell copyrightSellType(Integer copyrightSellType) {
        this.copyrightSellType = copyrightSellType;
        return this;
    }

    public void setCopyrightSellType(Integer copyrightSellType) {
        this.copyrightSellType = copyrightSellType;
    }

    public Long getSellingPrice() {
        return sellingPrice;
    }

    public ProjectSell sellingPrice(Long sellingPrice) {
        this.sellingPrice = sellingPrice;
        return this;
    }

    public void setSellingPrice(Long sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public String getTxHash() {
        return txHash;
    }

    public ProjectSell txHash(String txHash) {
        this.txHash = txHash;
        return this;
    }

    public void setTxHash(String txHash) {
        this.txHash = txHash;
    }

    public String getDescription() {
        return description;
    }

    public ProjectSell description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getOwnerUserId() {
        return ownerUserId;
    }

    public ProjectSell ownerUserId(Long ownerUserId) {
        this.ownerUserId = ownerUserId;
        return this;
    }

    public void setOwnerUserId(Long ownerUserId) {
        this.ownerUserId = ownerUserId;
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public ProjectSell buyerId(Long buyerId) {
        this.buyerId = buyerId;
        return this;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public Integer getIsSell() {
        return isSell;
    }

    public ProjectSell isSell(Integer isSell) {
        this.isSell = isSell;
        return this;
    }

    public void setIsSell(Integer isSell) {
        this.isSell = isSell;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public ProjectSell createTime(Long createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public ProjectSell updateTime(Long updateTime) {
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
        ProjectSell projectSell = (ProjectSell) o;
        if (projectSell.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), projectSell.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProjectSell{" +
            "id=" + getId() +
            ", projectId=" + getProjectId() +
            ", copyrightSellType=" + getCopyrightSellType() +
            ", sellingPrice=" + getSellingPrice() +
            ", txHash='" + getTxHash() + "'" +
            ", description='" + getDescription() + "'" +
            ", ownerUserId=" + getOwnerUserId() +
            ", buyerId=" + getBuyerId() +
            ", isSell=" + getIsSell() +
            ", createTime=" + getCreateTime() +
            ", updateTime=" + getUpdateTime() +
            "}";
    }
}
