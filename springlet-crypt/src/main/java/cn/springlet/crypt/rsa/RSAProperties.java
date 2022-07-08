package cn.springlet.crypt.rsa;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

//import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * rsa配置
 *
 * @author watermelon
 * @time 2021/11/29
 */
@ConfigurationProperties("springlet.crypt.rsa")
@Configuration
//@RefreshScope
public class RSAProperties {
    /**
     * 是否开启
     */
    private boolean enabled = false;

    /**
     * 私钥
     */
    private String privateKey;

    /**
     * 公钥
     */
    private String publicKey;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
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
}
