package cn.springlet.rocketmq.enums;


import cn.hutool.core.util.IdUtil;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Objects;

/**
 * 消息 msgKey
 *
 * @author watermelon
 * @time 2021/8/25
 */
public interface MsgKeyEnum<T> {

    String SPLIT = "::";

    /**
     * 获取业务类型
     *
     * @return
     */
    String getBusinessType();

    /**
     * 描述
     *
     * @return
     */
    String getDesc();

    /**
     * 获取 唯一id
     *
     * @return
     */
    default String getId() {
        return IdUtil.getSnowflakeNextIdStr();
    }

    /**
     * 获取 msgKey
     *
     * @return
     */
    default String getMsgKey() {
        return getBusinessType() + SPLIT + getId();
    }


    /**
     * 根据 msgKey 获取枚举
     *
     * @param msgKey
     * @param enumClass
     * @param <E>
     * @return
     */
    static <E extends MsgKeyEnum> E getMsgKeyEnum(String msgKey, Class<E> enumClass) {
        if (msgKey == null) {
            return null;
        }
        //第一段为 businessType
        String[] split = msgKey.split(SPLIT);
        return valueOf(enumClass, split[0]);
    }


    /**
     * 找到匹配的那个枚举
     *
     * @param enumClass
     * @param value
     * @return
     */
    static <E extends MsgKeyEnum> E valueOf(Class<E> enumClass, Object value) {
        E[] es = enumClass.getEnumConstants();
        return Arrays.stream(es).filter((e) -> equalsValue(value, e.getBusinessType())).findAny().orElse(null);
    }

    /**
     * 值比较
     *
     * @param sourceValue
     * @param targetValue
     * @return
     */
    static boolean equalsValue(Object sourceValue, Object targetValue) {
        String sValue = Objects.toString(sourceValue);
        String tValue = Objects.toString(targetValue);
        if (sourceValue instanceof Number && targetValue instanceof Number
                && new BigDecimal(sValue).compareTo(new BigDecimal(tValue)) == 0) {
            return true;
        }
        return Objects.equals(sValue, tValue);
    }
}
