package cn.springlet.rocketmq.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.springlet.rocketmq.enums.MsgKeyEnum;
import cn.springlet.rocketmq.producer.ProducerService;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.SendResult;

/**
 * mq send 静态方法
 *
 * @author watermelon
 * @time 2022/3/31
 */
public class MqSendUtil {

    private static volatile ProducerService producerService;

    private static void init() {
        if (producerService == null) {
            synchronized (ProducerService.class) {
                if (producerService == null) {
                    producerService = SpringUtil.getBean(ProducerService.class);
                }
            }
        }
    }


    /**
     * 发送消息
     *
     * @param messageBody 消息体
     * @param msgKey      消息key
     */
    public static void sendMsg(MsgKeyEnum msgKey, Object messageBody) {
        init();
        producerService.sendMsg(msgKey, messageBody);
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
    public static void sendDelayMsg(MsgKeyEnum msgKey, Object messageBody, long delayTime) {
        init();
        producerService.sendDelayMsg(msgKey, messageBody, delayTime);
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
    public static void sendTimeMsg(MsgKeyEnum msgKey, Object messageBody, String datetime) {
        init();
        producerService.sendTimeMsg(msgKey, messageBody, datetime);
    }


    /**
     * 发送单向消息
     *
     * @param messageBody 消息体
     * @param msgKey      消息key
     */
    public static void sendOneWayMsg(MsgKeyEnum msgKey, Object messageBody) {
        init();
        producerService.sendOneWayMsg(msgKey, messageBody);
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
    public static void sendOneWayDelayMsg(MsgKeyEnum msgKey, Object messageBody, long delayTime) {
        init();
        producerService.sendOneWayDelayMsg(msgKey, messageBody, delayTime);
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
    public static void sendOneWayTimeMsg(MsgKeyEnum msgKey, Object messageBody, String datetime) {
        init();
        producerService.sendOneWayTimeMsg(msgKey, messageBody, datetime);
    }

    /**
     * 发送消息
     *
     * @param messageBody 消息体
     * @param msgKey      消息key
     * @param msgTag      消息tag
     */
    public static void sendMsg(MsgKeyEnum msgKey, String msgTag, Object messageBody) {
        init();
        producerService.sendMsg(msgKey, msgTag, messageBody);
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
    public static void sendDelayMsg(MsgKeyEnum msgKey, String msgTag, Object messageBody, long delayTime) {
        init();
        producerService.sendDelayMsg(msgKey, msgTag, messageBody, delayTime);
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
    public static void sendTimeMsg(MsgKeyEnum msgKey, String msgTag, Object messageBody, String datetime) {
        init();
        producerService.sendTimeMsg(msgKey, msgTag, messageBody, datetime);
    }


    /**
     * 发送单向消息
     *
     * @param messageBody 消息体
     * @param msgTag      消息tag
     * @param msgKey      消息key
     */
    public static void sendOneWayMsg(MsgKeyEnum msgKey, String msgTag, Object messageBody) {
        init();
        producerService.sendOneWayMsg(msgKey, msgTag, messageBody);
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
    public static void sendOneWayDelayMsg(MsgKeyEnum msgKey, String msgTag, Object messageBody, long delayTime) {
        init();
        producerService.sendOneWayDelayMsg(msgKey, msgTag, messageBody, delayTime);
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
    public static void sendOneWayTimeMsg(MsgKeyEnum msgKey, String msgTag, Object messageBody, String datetime) {
        init();
        producerService.sendOneWayTimeMsg(msgKey, msgTag, messageBody, datetime);
    }


    /**
     * 普通消息发送发放
     * 可自定义消息发送
     *
     * @param msg      消息
     * @param isOneWay 是否单向发送 单项消息没有返回信息
     */
    public static SendResult send(Message msg, Boolean isOneWay) {
        init();
        return producerService.send(msg, isOneWay);
    }


    /**
     * 构建消息 ,可自定义构建消息
     *
     * @param topic
     * @param msgTag
     * @param msgKey
     * @param messageBody
     * @return
     */
    public static Message buildMessage(String topic, String msgTag, MsgKeyEnum msgKey, Object messageBody) {
        init();
        return producerService.buildMessage(topic, msgTag, msgKey, messageBody);
    }
}
