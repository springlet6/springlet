package cn.springlet.redis.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * redission 配置
 *
 * @author watermelon
 * @time 2020/10/20
 */
@Configuration
public class SpringletRedissonConfig {


    /**
     * Redission
     */
    @Bean
    @SuppressWarnings(value = {"all"})
    protected RedissonClient redissonClient(RedisProperties redisProperties) {
        Config config = new Config();
        config.useSingleServer()
                // use "rediss://" for SSL connection
                .setAddress("redis://" + redisProperties.getHost() + ":" + redisProperties.getPort())
                .setPassword(redisProperties.getPassword());
        //看门狗时间为3s
        config.setLockWatchdogTimeout(3000L);
        RedissonClient redisson = Redisson.create(config);
        return redisson;
    }
}
