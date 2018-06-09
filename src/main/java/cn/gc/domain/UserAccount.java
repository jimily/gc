package cn.gc.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A UserAccount.
 */
@Entity
@Table(name = "biz_user_account")
public class UserAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @NotNull
    @Column(name = "asset", nullable = false)
    private Long asset;

    @NotNull
    @Column(name = "pub_key", nullable = false)
    private String pubKey;

    @NotNull
    @Column(name = "private_key", nullable = false)
    private String privateKey;

    @NotNull
    @Column(name = "blockchain_address", nullable = false)
    private String blockchainAddress;

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

    public UserAccount userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getAsset() {
        return asset;
    }

    public UserAccount asset(Long asset) {
        this.asset = asset;
        return this;
    }

    public void setAsset(Long asset) {
        this.asset = asset;
    }

    public String getPubKey() {
        return pubKey;
    }

    public UserAccount pubKey(String pubKey) {
        this.pubKey = pubKey;
        return this;
    }

    public void setPubKey(String pubKey) {
        this.pubKey = pubKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public UserAccount privateKey(String privateKey) {
        this.privateKey = privateKey;
        return this;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getBlockchainAddress() {
        return blockchainAddress;
    }

    public UserAccount blockchainAddress(String blockchainAddress) {
        this.blockchainAddress = blockchainAddress;
        return this;
    }

    public void setBlockchainAddress(String blockchainAddress) {
        this.blockchainAddress = blockchainAddress;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public UserAccount createTime(Long createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public UserAccount updateTime(Long updateTime) {
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
        UserAccount userAccount = (UserAccount) o;
        if (userAccount.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userAccount.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserAccount{" +
            "id=" + getId() +
            ", userId=" + getUserId() +
            ", asset=" + getAsset() +
            ", pubKey='" + getPubKey() + "'" +
            ", privateKey='" + getPrivateKey() + "'" +
            ", blockchainAddress='" + getBlockchainAddress() + "'" +
            ", createTime=" + getCreateTime() +
            ", updateTime=" + getUpdateTime() +
            "}";
    }
}
