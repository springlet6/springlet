package cn.springlet.rocketmq.consumer;

import cn.springlet.rocketmq.enums.MsgKeyEnum;
import cn.springlet.rocketmq.log.LogUtil;
import cn.springlet.rocketmq.util.MsgSerializeUtil;
import com.aliyun.openservices.ons.api.Message;

/**
 * 消息业务 各服务实现此类 分发业务消息
 *
 * @author watermelon
 * @time 2021/8/25
 */
public interface BaseMessageListenerHandler<T> {


    /**
     * 具体 tag 的业务操作
     *
     * @param message
     */
    void businessOpt(T msgKeyEnum, Message message);


    /**
     * 获取业务类型
     */
    Class<T> getMsgKeyEnumClass();


    /**
     * 获取 消息参数对象
     *
     * @param message
     * @param clazz
     * @param <B>
     * @return
     */
    default <B> B getBodyObj(MsgKeyEnum msgKeyEnum, Message message, Class<B> clazz) {
        B bodyObj = MsgSerializeUtil.objectDeserialize(message.getBody(), clazz);
        LogUtil.receive(message, msgKeyEnum, bodyObj);
        return bodyObj;
    }
}
