package cn.springlet.fast.config.mq;

import cn.springlet.fast.config.mq.enums.CustomMsgKeyEnum;
import cn.springlet.rocketmq.consumer.BaseMessageListenerHandler;
import com.aliyun.openservices.ons.api.Message;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 消息分发
 *
 * @author watermelon
 * @time 2021/8/25
 */
@Component
@AllArgsConstructor
@Slf4j
public class MessageListenerHandler implements BaseMessageListenerHandler<CustomMsgKeyEnum> {

    @Override
    public void businessOpt(CustomMsgKeyEnum msgKeyEnum, Message message) {
        switch (msgKeyEnum) {
            case LOG:
                String withdrawDetailNo = getBodyObj(msgKeyEnum, message, String.class);

                break;
        }
    }

    @Override
    public Class<CustomMsgKeyEnum> getMsgKeyEnumClass() {
        return CustomMsgKeyEnum.class;
    }
}
