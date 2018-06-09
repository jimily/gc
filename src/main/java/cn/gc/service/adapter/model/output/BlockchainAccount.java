package cn.gc.service.adapter.model.output;

/**
 * @author wangjingru
 * @description 区块链账户
 * @email wangjingru@bubi.cn
 * @creatTime 2018/6/9 17:08
 * @since 1.0.0
 */
public class BlockchainAccount {
    private String address;
    private String privateKey;
    private String publicKey;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    @Override
    public String toString() {
        return "BlockchainAccount{" +
            "address='" + address + '\'' +
            ", privateKey='" + privateKey + '\'' +
            ", publicKey='" + publicKey + '\'' +
            '}';
    }
}
