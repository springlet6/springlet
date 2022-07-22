package cn.springlet.core.util;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author watermelon
 * @time 2021/03/09
 */
@Slf4j
public class EnumUtils {

    /**
     * 根据枚举的字符串获取枚举的值
     *
     * @param className 包名+类名
     * @return
     * @throws Exception
     */
    public static List<Map<String, Object>> getAllEnumByClassName(String className) {
        if (StrUtil.isEmpty(className)) {
            return null;
        }
        try {
            // 1.得到枚举类对象
            Class<Enum> clz = (Class<Enum>) Class.forName(className);
            // 2.得到所有枚举常量
            Object[] objects = clz.getEnumConstants();
            Method name = getMethod(clz, "name");
            Method getKey = getMethod(clz, "getKey");
            Method getValue = getMethod(clz, "getValue");
            List<Map<String, Object>> list = new ArrayList<>();
            Map<String, Object> map = null;
            for (Object obj : objects) {
                map = new HashMap<>(10);
                map.put("name", name != null ? name.invoke(obj) : "");
                map.put("key", getKey != null ? getKey.invoke(obj) : "");
                map.put("value", getValue != null ? getValue.invoke(obj) : "");
                list.add(map);
            }
            return list;
        } catch (Exception e) {
            log.error("EnumUtils->getAllEnumByClassName异常", e);
            return null;
        }
    }

    private static Method getMethod(Class<Enum> clz, String name) {
        try {
            return clz.getMethod(name);
        } catch (Exception e) {
            return null;
        }
    }
}
