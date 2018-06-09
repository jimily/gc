package cn.gc.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author wangjingru
 * @description TODO
 * @email wangjingru@bubi.cn
 * @creatTime 2018/6/9 21:40
 * @since 1.0.0
 */
@Configuration
@ConfigurationProperties(prefix = "bc")
public class BlockchainConfig {
    private String address;
    private String priKey;
    private String pubKey;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPriKey() {
        return priKey;
    }

    public void setPriKey(String priKey) {
        this.priKey = priKey;
    }

    public String getPubKey() {
        return pubKey;
    }

    public void setPubKey(String pubKey) {
        this.pubKey = pubKey;
    }
}
