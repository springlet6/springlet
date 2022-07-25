package cn.springlet.rocketmq.consumer;

import cn.springlet.core.exception.web_return.RocketMqException;
import cn.springlet.rocketmq.enums.MsgKeyEnum;
import cn.springlet.rocketmq.log.LogUtil;
import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * rocketMq 消息监听器
 *
 * @author watermelon
 * @time 2022/3/30
 */
@Component
@Slf4j
@AllArgsConstructor
public class MqMessageListener implements MessageListener {

    private final BaseMessageListenerHandler baseMessageListenerHandler;

    @Override
    public Action consume(Message message, ConsumeContext context) {
        try {
            MsgKeyEnum msgKeyEnum = MsgKeyEnum.getMsgKeyEnum(message.getKey(), baseMessageListenerHandler.getMsgKeyEnumClass());
            if (msgKeyEnum == null) {
                throw new RocketMqException("msgKeyEnum 为空");
            }
            baseMessageListenerHandler.businessOpt(msgKeyEnum, message);

            LogUtil.consumerSuccess(message, msgKeyEnum);
            return Action.CommitMessage;
        } catch (Exception e) {
            LogUtil.consumerError(message, e);
            return Action.ReconsumeLater;
        }
    }
}
