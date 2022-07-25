package cn.springlet.rocketmq.consumer;

import cn.springlet.rocketmq.config.MqConfig;
import com.aliyun.openservices.ons.api.MessageListener;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.ons.api.bean.ConsumerBean;
import com.aliyun.openservices.ons.api.bean.Subscription;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * rocketMq 消息消费者配置
 *
 * @author watermelon
 * @time 2022/3/30
 */
@Configuration
@AllArgsConstructor
public class ConsumerClient {

    private final MqConfig mqConfig;

    //普通消息监听器，Consumer注册消息监听器来订阅消息.
    private final MqMessageListener messageListener;

    @Bean(initMethod = "start", destroyMethod = "shutdown")
    public ConsumerBean buildConsumer() {
        ConsumerBean consumerBean = new ConsumerBean();
        //配置文件
        Properties properties = mqConfig.getMqProperties();
        properties.setProperty(PropertyKeyConst.GROUP_ID, mqConfig.getGroupId());
        //将消费者线程数固定为20个 20为默认值
        properties.setProperty(PropertyKeyConst.ConsumeThreadNums, "20");
        consumerBean.setProperties(properties);
        //订阅消息
        Map<Subscription, MessageListener> subscriptionTable = new HashMap<Subscription, MessageListener>();

        //订阅定时/延时消息
        Subscription subscriptionTime = new Subscription();
        subscriptionTime.setTopic(mqConfig.getTopic());
        subscriptionTime.setExpression(mqConfig.getTag());
        subscriptionTable.put(subscriptionTime, messageListener);

        consumerBean.setSubscriptionTable(subscriptionTable);
        return consumerBean;
    }
}
