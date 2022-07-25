package cn.springlet.rocketmq.config;

import com.aliyun.openservices.ons.api.PropertyKeyConst;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * rocketMq 配置
 *
 * @author watermelon
 * @time 2022/3/30
 */
@Configuration
@ConfigurationProperties(prefix = "springlet.rocketmq")
//@RefreshScope
public class MqConfig {

    /**
     * 阿里云 accessKey
     */
    private String accessKey;

    /**
     * 阿里云 secretKey
     */
    private String secretKey;

    /**
     * 实例TCP 协议公网接入地址（实际项目，填写自己阿里云MQ的公网地址）
     */
    private String nameSrvAddr;

    /**
     * 阿里云 实例 ID
     */
    private String instanceId;


    /**
     * 消息topic
     * 共用一个topic
     */
    private String topic;

    /**
     * 消息groupId
     * 每个环境、每个服务需要新增一个group  共 n * n 个group
     * 例 GID-test-cutepet-product-group
     */
    private String groupId;

    /**
     * 标签
     * 每个环境、每个服务需要不同的 tag 共 n * n 个tag
     * 例 test-cutepet-product
     */
    private String tag;

    /**
     * 发送消息超时时间
     */
    private String sendTimeOut = "4000";

    /**
     * 基础配置信息
     *
     * @return
     */
    public Properties getMqProperties() {
        Properties properties = new Properties();
        properties.setProperty(PropertyKeyConst.AccessKey, this.accessKey);
        properties.setProperty(PropertyKeyConst.SecretKey, this.secretKey);
        properties.setProperty(PropertyKeyConst.NAMESRV_ADDR, this.nameSrvAddr);
        properties.setProperty(PropertyKeyConst.INSTANCE_ID, this.instanceId);
        //设置发送超时时间
        properties.setProperty(PropertyKeyConst.SendMsgTimeoutMillis, this.sendTimeOut);
        return properties;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getNameSrvAddr() {
        return nameSrvAddr;
    }

    public void setNameSrvAddr(String nameSrvAddr) {
        this.nameSrvAddr = nameSrvAddr;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getSendTimeOut() {
        return sendTimeOut;
    }

    public void setSendTimeOut(String sendTimeOut) {
        this.sendTimeOut = sendTimeOut;
    }
}
