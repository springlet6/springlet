package cn.springlet.rocketmq.util;


import cn.springlet.core.exception.web_return.RocketMqException;
import com.aliyun.openservices.ons.api.impl.util.MsgConvertUtil;
import lombok.extern.slf4j.Slf4j;


/**
 * 消息序列化 util
 *
 * @author watermelon
 * @time 2022/3/30
 */
@Slf4j
public class MsgSerializeUtil {

    /**
     * 序列化
     *
     * @param object
     * @return
     */
    public static byte[] objectSerialize(Object object) {
        try {
            return MsgConvertUtil.objectSerialize(object);
        } catch (Exception e) {
            log.error("MsgSerializeUtil:消息序列化出错", e);
            throw new RocketMqException(e.getMessage());
        }
    }

    /**
     * 反序列化
     *
     * @param <T>
     * @param bytes
     * @param clazz
     * @return
     */
    public static <T> T objectDeserialize(byte[] bytes, Class<T> clazz) {
        try {
            return (T) MsgConvertUtil.objectDeserialize(bytes);
        } catch (Exception e) {
            log.error("MsgSerializeUtil:消息反序列化出错", e);
            throw new RocketMqException(e.getMessage());
        }
    }
}
