package cn.springlet.rocketmq.producer;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.springlet.core.exception.web_return.ReturnMsgException;
import cn.springlet.rocketmq.config.MqConfig;
import cn.springlet.rocketmq.enums.MsgKeyEnum;
import cn.springlet.rocketmq.log.LogUtil;
import cn.springlet.rocketmq.util.MsgSerializeUtil;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.SendResult;
import com.aliyun.openservices.ons.api.bean.ProducerBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * rocketMq 消息生产者
 * 发送消息
 *
 * @author watermelon
 * @time 2022/3/30
 */
@Component
@Slf4j
public class ProducerService {

    public ProducerService(MqConfig config, ProducerBean producer) {
        this.config = config;
        this.producer = producer;
    }

    private final MqConfig config;

    private final ProducerBean producer;

    /**
     * 发送消息
     *
     * @param messageBody 消息体
     * @param msgKey      消息key
     */
    public void sendMsg(MsgKeyEnum msgKey, Object messageBody) {
        Message msg = buildMessage(null, null, msgKey, messageBody);
        //立即发送
        msg.setStartDeliverTime(0L);
        this.send(msg, Boolean.FALSE);
    }


    /**
     * 发送消息 延时消息
     * <p>
     *
     * @param msgKey      消息key
     * @param messageBody 消息体
     * @param delayTime   最大延迟时间为7天.
     *                    延迟投递: 延迟3s投递, 设置为: 3000;
     */
    public void sendDelayMsg(MsgKeyEnum msgKey, Object messageBody, long delayTime) {
        Message msg = buildMessage(null, null, msgKey, messageBody);
        msg.setStartDeliverTime(System.currentTimeMillis() + delayTime);
        this.send(msg, Boolean.FALSE);
    }

    /**
     * 发送消息 定时消息
     * <p>
     *
     * @param msgKey      消息key
     * @param messageBody 消息体
     * @param datetime    最大延迟时间为7天.
     *                    定时投递:  支持格式 {@link DateUtil#parse(CharSequence)}
     */
    public void sendTimeMsg(MsgKeyEnum msgKey, Object messageBody, String datetime) {
        Message msg = buildMessage(null, null, msgKey, messageBody);
        msg.setStartDeliverTime(DateUtil.parse(datetime).getTime());
        this.send(msg, Boolean.FALSE);
    }


    /**
     * 发送单向消息
     *
     * @param messageBody 消息体
     * @param msgKey      消息key
     */
    public void sendOneWayMsg(MsgKeyEnum msgKey, Object messageBody) {
        Message msg = buildMessage(null, null, msgKey, messageBody);
        //立即发送
        msg.setStartDeliverTime(0L);
        this.send(msg, Boolean.TRUE);
    }


    /**
     * 发送单向消息 延时消息
     * <p>
     *
     * @param msgKey      消息key
     * @param messageBody 消息体
     * @param delayTime   最大延迟时间为7天.
     *                    延迟投递: 延迟3s投递, 设置为: 3000;
     */
    public void sendOneWayDelayMsg(MsgKeyEnum msgKey, Object messageBody, long delayTime) {
        Message msg = buildMessage(null, null, msgKey, messageBody);
        msg.setStartDeliverTime(System.currentTimeMillis() + delayTime);
        this.send(msg, Boolean.TRUE);
    }

    /**
     * 发送单向消息 定时消息
     * <p>
     *
     * @param msgKey      消息key
     * @param messageBody 消息体
     * @param datetime    最大延迟时间为7天.
     *                    定时投递:  支持格式 {@link DateUtil#parse(CharSequence)}
     */
    public void sendOneWayTimeMsg(MsgKeyEnum msgKey, Object messageBody, String datetime) {
        Message msg = buildMessage(null, null, msgKey, messageBody);
        msg.setStartDeliverTime(DateUtil.parse(datetime).getTime());
        this.send(msg, Boolean.TRUE);
    }


    /**
     * 发送消息
     *
     * @param messageBody 消息体
     * @param msgKey      消息key
     * @param msgTag      消息tag
     */
    public void sendMsg(MsgKeyEnum msgKey, String msgTag, Object messageBody) {
        Message msg = buildMessage(null, msgTag, msgKey, messageBody);
        //立即发送
        msg.setStartDeliverTime(0L);
        this.send(msg, Boolean.FALSE);
    }


    /**
     * 发送消息 延时消息
     * <p>
     *
     * @param msgKey      消息key
     * @param msgTag      消息tag
     * @param messageBody 消息体
     * @param delayTime   最大延迟时间为7天.
     *                    延迟投递: 延迟3s投递, 设置为: 3000;
     */
    public void sendDelayMsg(MsgKeyEnum msgKey, String msgTag, Object messageBody, long delayTime) {
        Message msg = buildMessage(null, msgTag, msgKey, messageBody);
        msg.setStartDeliverTime(System.currentTimeMillis() + delayTime);
        this.send(msg, Boolean.FALSE);
    }

    /**
     * 发送消息 定时消息
     * <p>
     *
     * @param msgKey      消息key
     * @param msgTag      消息tag
     * @param messageBody 消息体
     * @param datetime    最大延迟时间为7天.
     *                    定时投递:  支持格式 {@link DateUtil#parse(CharSequence)}
     */
    public void sendTimeMsg(MsgKeyEnum msgKey, String msgTag, Object messageBody, String datetime) {
        Message msg = buildMessage(null, msgTag, msgKey, messageBody);
        msg.setStartDeliverTime(DateUtil.parse(datetime).getTime());
        this.send(msg, Boolean.FALSE);
    }


    /**
     * 发送单向消息
     *
     * @param messageBody 消息体
     * @param msgKey      消息key
     * @param msgTag      消息tag
     */
    public void sendOneWayMsg(MsgKeyEnum msgKey, String msgTag, Object messageBody) {
        Message msg = buildMessage(null, msgTag, msgKey, messageBody);
        //立即发送
        msg.setStartDeliverTime(0L);
        this.send(msg, Boolean.TRUE);
    }


    /**
     * 发送单向消息 延时消息
     * <p>
     *
     * @param msgKey      消息key
     * @param msgTag      消息tag
     * @param messageBody 消息体
     * @param delayTime   最大延迟时间为7天.
     *                    延迟投递: 延迟3s投递, 设置为: 3000;
     */
    public void sendOneWayDelayMsg(MsgKeyEnum msgKey, String msgTag, Object messageBody, long delayTime) {
        Message msg = buildMessage(null, msgTag, msgKey, messageBody);
        msg.setStartDeliverTime(System.currentTimeMillis() + delayTime);
        this.send(msg, Boolean.TRUE);
    }

    /**
     * 发送单向消息 定时消息
     * <p>
     *
     * @param msgKey      消息key
     * @param msgTag      消息tag
     * @param messageBody 消息体
     * @param datetime    最大延迟时间为7天.
     *                    定时投递:  支持格式 {@link DateUtil#parse(CharSequence)}
     */
    public void sendOneWayTimeMsg(MsgKeyEnum msgKey, String msgTag, Object messageBody, String datetime) {
        Message msg = buildMessage(null, msgTag, msgKey, messageBody);
        msg.setStartDeliverTime(DateUtil.parse(datetime).getTime());
        this.send(msg, Boolean.TRUE);
    }


    /**
     * 普通消息发送发放
     * 可自定义消息发送
     *
     * @param msg      消息
     * @param isOneWay 是否单向发送 单项消息没有返回信息
     */
    public SendResult send(Message msg, Boolean isOneWay) {

        //由于在 oneway 方式发送消息时没有请求应答处理，一旦出现消息发送失败，则会因为没有重试而导致数据丢失。
        if (isOneWay) {
            producer.sendOneway(msg);
            success(msg, "单向消息MsgId不返回");

            return null;
        } else {
            //可靠同步发送
            SendResult sendResult = producer.send(msg);
            //获取发送结果，不抛异常即发送成功
            if (sendResult != null) {
                success(msg, sendResult.getMessageId());
                return sendResult;
            } else {
                error(msg, null);
                return null;
            }
        }
    }

    /**
     * 构建消息 ,可自定义构建消息
     *
     * @param topic
     * @param msgTag      为空，默认取本服务的tag
     * @param msgKey
     * @param messageBody
     * @return
     */
    public Message buildMessage(String topic, String msgTag, MsgKeyEnum msgKey, Object messageBody) {
        if (StrUtil.isBlank(topic)) {
            topic = config.getTopic();
        }
        if (StrUtil.isBlank(msgTag)) {
            msgTag = config.getTag();
        }
        if (messageBody == null) {
            log.error("rocketmq->发送消息->失败,消息体为空 -- Topic={} ,tag={}, Key={}", topic, msgTag, msgKey.getMsgKey());
            throw new ReturnMsgException("消息体不能为空");
        }
        return new Message(config.getTopic(), msgTag, msgKey.getMsgKey(), getSerializeMessageBody(messageBody));
    }


    private byte[] getSerializeMessageBody(Object messageBody) {
        return MsgSerializeUtil.objectSerialize(messageBody);
    }

    /**
     * error日志
     *
     * @param msg
     * @param e
     */
    private void error(Message msg, Exception e) {
        LogUtil.sendError(msg, e);
    }

    /**
     * success 日志
     *
     * @param msg
     * @param messageId
     */
    private void success(Message msg, String messageId) {
        LogUtil.sendSuccess(msg, messageId);
    }
}
