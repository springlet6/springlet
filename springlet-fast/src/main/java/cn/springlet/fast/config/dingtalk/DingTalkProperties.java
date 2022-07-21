package cn.springlet.fast.config.dingtalk;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 钉钉消息 默认异常钉钉推送配置
 *
 * @author watermelon
 * @time 2022/4/12
 */
@Setter
@Getter
@Configuration
@ConfigurationProperties("springlet.dingtalk.error")
public class DingTalkProperties {

    /**
     * 加签密钥
     */
    private String secret;

    /**
     * Webhook 地址
     */
    private String webhook;
}
