package cn.springlet.crypt.rsa;

import cn.hutool.crypto.asymmetric.RSA;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * rsa 解密
 *
 * @author watermelon
 * @time 2021/11/29
 */
@Slf4j
@Configuration
@ConditionalOnProperty(name = "springlet.crypt.rsa.enabled", matchIfMissing = false)
public class RSAConfig {
    public static final String RSA_BEAN_NAME = "springletRsa";

    @Bean(RSAConfig.RSA_BEAN_NAME)
    @SuppressWarnings(value = {"all"})
    public RSA rsa(RSAProperties rsaProperties) {
        return new RSA(rsaProperties.getPrivateKey(), rsaProperties.getPublicKey());
    }
}
