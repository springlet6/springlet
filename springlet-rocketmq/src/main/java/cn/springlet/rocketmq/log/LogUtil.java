package cn.springlet.rocketmq.log;

import cn.springlet.rocketmq.enums.MsgKeyEnum;
import com.aliyun.openservices.ons.api.Message;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * rocketmq 日志处理
 *
 * @author watermelon
 * @time 2022/3/31
 */
@Slf4j
public class LogUtil implements Serializable {
    /**
     * 发送消息成功
     *
     * @param msg
     * @param messageId
     */
    public static void sendSuccess(Message msg, String messageId) {
        log.info("rocketmq->发送消息->成功 -- Topic={}, tag={}, Key={}, msgId={}", msg.getTopic(), msg.getTag(), msg.getKey(), messageId);
    }

    /**
     * 发送消息失败
     *
     * @param msg
     * @param e
     */
    public static void sendError(Message msg, Exception e) {
        log.error("rocketmq->发送消息->失败 -- Topic={} ,tag={}, Key={}", msg.getTopic(), msg.getTag(), msg.getKey());
        if (e != null) {
            log.error("rocketmq->发送消息->失败", e);
        }
    }


    /**
     * 接收消息
     *
     * @param msg
     * @param msgKeyEnum
     */
    public static void receive(Message msg, MsgKeyEnum msgKeyEnum, Object bodyObj) {
        log.info("rocketmq->收到消息 -- 业务类型:{}, Topic={}, tag={}, Key={}, msgId={},body={}", msgKeyEnum.getDesc(), msg.getTopic(), msg.getTag(), msg.getMsgID(), msg.getKey(), bodyObj);
    }


    /**
     * 消息消费成功
     *
     * @param msg
     * @param msgKeyEnum
     */
    public static void consumerSuccess(Message msg, MsgKeyEnum msgKeyEnum) {
        log.info("rocketmq->消费消息->成功 -- 业务类型:{}, Topic={}, tag={}, Key={}, msgId={}", msgKeyEnum.getDesc(), msg.getTopic(), msg.getTag(), msg.getMsgID(), msg.getKey());
    }

    /**
     * 消息消费失败
     *
     * @param msg
     * @param e
     */
    public static void consumerError(Message msg, Exception e) {
        log.info("rocketmq->消费消息->失败 -- Topic={}, tag={}, Key={}, msgId={}", msg.getTopic(), msg.getTag(), msg.getMsgID(), msg.getKey());
        if (e != null) {
            log.error("rocketmq->消费消息->失败", e);
        }
    }
}
