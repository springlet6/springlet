package cn.springlet.core.enums;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Objects;

/**
 * 数据库枚举接口
 * 实现此接口的枚举可直接与数据库中的字段 做映射
 * 例：
 * 定义一个接口：
 * *public enum Status implements DatabaseEnum<String> {
 * OK("0", "正常");
 * <p>
 * private final String k;
 * private final String v;
 * <p>
 * UserStatus(String k, String v) {
 * this.k = k;
 * this.k = v;
 * }
 * public String getKey() {
 * return code;
 * }
 * }
 * <p>
 * 基于 mybatis-plus使用： 用在实体类
 * 需要 在 指定表名的注解中 开启 自动生成ResultMap
 * *@TableName(value = "user", autoResultMap = true)
 * <p>
 * *@TableField(typeHandler = DataBaseEnumTypeHandler.class)
 * *private Status name;
 * <p>
 * <p>
 * 基于 mybatis 使用：用在xml文件
 * *<resultMap id="BaseResultMap" type="com.lhs.external.service.application.dal.entity.DemoTestDO">
 * *    <result column="status" property="status" typeHandler="com.lhs.common.mybatisplus.handles.DataBaseEnumTypeHandler"/>
 * *</resultMap>
 * *
 *
 * @author watermelon
 * @time 2020/10/22
 */
public interface DatabaseEnum<T extends Serializable> {

    /**
     * 数据库存储的枚举key
     *
     * @return
     */
    T getDataBaseKey();

    /**
     * 找到匹配的那个枚举
     *
     * @param enumClass
     * @param value
     * @return
     */
    static <E extends DatabaseEnum> E valueOf(Class<E> enumClass, Object value) {
        E[] es = enumClass.getEnumConstants();
        return Arrays.stream(es).filter((e) -> equalsValue(value, e.getDataBaseKey())).findAny().orElse(null);
    }

    /**
     * 值比较
     *
     * @param sourceValue 数据库字段值
     * @param targetValue 当前枚举属性值
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
