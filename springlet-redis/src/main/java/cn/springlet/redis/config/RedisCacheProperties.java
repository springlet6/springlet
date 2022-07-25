package cn.springlet.redis.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * spring redis cache 相关配置
 *
 * @author watermelon
 * @time 2020/10/22
 */
@ConfigurationProperties("springlet.cache")
@Configuration
public class RedisCacheProperties {

    /**
     * 缓存超时时间
     * 默认 6 个小时
     */
    private Long timeout = 60 * 60 * 6L;

    public Long getTimeout() {
        return timeout;
    }

    public void setTimeout(Long timeout) {
        this.timeout = timeout;
    }
}
