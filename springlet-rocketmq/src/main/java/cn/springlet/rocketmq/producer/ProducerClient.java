package cn.springlet.rocketmq.producer;

import cn.springlet.rocketmq.config.MqConfig;
import com.aliyun.openservices.ons.api.bean.ProducerBean;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * rocketMq 配置
 *
 * @author watermelon
 * @time 2022/3/30
 */
@Configuration
@Slf4j
@AllArgsConstructor
public class ProducerClient {

    private final MqConfig mqConfig;


    @Bean(initMethod = "start", destroyMethod = "shutdown")
    public ProducerBean buildProducer() {

        //ProducerBean用于将Producer集成至Spring Bean中
        ProducerBean producer = new ProducerBean();
        producer.setProperties(mqConfig.getMqProperties());

        return producer;
    }
}
