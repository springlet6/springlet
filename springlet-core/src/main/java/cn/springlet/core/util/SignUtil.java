package cn.springlet.core.util;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.springlet.core.bean.vo.BaseVO;
import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * 签名校验 util
 *
 * @author watermelon
 * @since 2022/7/11
 */
public class SignUtil {


    public static final String SIGN_FIELD_NAME = "sign";
    public static final String SIGN_SECRET_KEY_FIELD_NAME = "secretKey";

    public static final String SIGN_SECRET_KEY = "g1BEQkXHqWj7JLEVOENhpFGmsO6ZZzjo";

    /**
     * 校验签名
     *
     * @param reqObj
     * @return
     */
    public static boolean checkSign(Object reqObj) {
        return checkSign(reqObj, SIGN_FIELD_NAME, true, SIGN_SECRET_KEY_FIELD_NAME, SIGN_SECRET_KEY);
    }


    /**
     * 校验签名
     *
     * @param reqObj
     * @param signFieldName
     * @return
     */
    public static boolean checkSign(Object reqObj, String signFieldName) {
        return checkSign(reqObj, signFieldName, true, SIGN_SECRET_KEY_FIELD_NAME, SIGN_SECRET_KEY);
    }

    /**
     * 校验签名
     *
     * @param reqObj
     * @param isRemoveSignField
     * @param signFieldName
     * @return
     */
    public static boolean checkSign(Object reqObj, boolean isRemoveSignField, String signFieldName) {
        return checkSign(reqObj, signFieldName, isRemoveSignField, SIGN_SECRET_KEY_FIELD_NAME, SIGN_SECRET_KEY);
    }

    /**
     * 校验签名
     *
     * @param reqObj
     * @param signFieldName
     * @param isRemoveSignField
     * @param secretKeyFieldName
     * @param secretKey
     * @return
     */
    public static boolean checkSign(Object reqObj, String signFieldName, boolean isRemoveSignField, String secretKeyFieldName, String secretKey) {
        Map<String, Object> stringObjectMap = BeanUtil.beanToMap(reqObj, false, true);
        Object sign = stringObjectMap.get(signFieldName);
        if (isRemoveSignField) {
            //删除签名字段
            stringObjectMap.remove(signFieldName);
        }

        String joinSignString = concatSignString(stringObjectMap, secretKeyFieldName, secretKey);
        if (sign.equals(SecureUtil.md5(joinSignString))) {
            return true;
        }
        return false;
    }


    /**
     * 获取签名的字符串
     *
     * @param map
     * @param secretKeyFieldName
     * @param secretKey
     * @return
     */
    public static String concatSignString(Map<String, Object> map, String secretKeyFieldName, String secretKey) {
        // 按照key升续排序，然后拼接参数
        Set<String> keySet = map.keySet();
        String[] keyArray = keySet.toArray(new String[0]);
        Arrays.sort(keyArray);
        StringBuilder sb = new StringBuilder();

        for (String k : keyArray) {
            if (StrUtil.isNotEmpty(sb.toString())) {
                sb.append("&");
            }
            Object v = map.get(k);
            System.out.println(JSON.toJSONString(v));
            sb.append(k).append("=").append(v instanceof Collection || (isNotGeneralType(v.getClass())) ? JSON.toJSONString(v) : v);
        }
        sb.append(secretKeyFieldName).append("=").append(secretKey);
        return sb.toString();
    }


    /**
     * 排除基础类型、jdk类型、枚举类型的字段
     *
     * @param clazz
     * @return
     */
    private static boolean isNotGeneralType(Class<?> clazz) {
        return clazz != null
                && clazz.getClassLoader() != null
                && !clazz.isPrimitive()
                && clazz.getPackage() != null
                && !clazz.isEnum()
                && !StringUtils.startsWith(clazz.getPackage().getName(), "javax.")
                && !StringUtils.startsWith(clazz.getPackage().getName(), "java.")
                && !StringUtils.startsWith(clazz.getName(), "javax.")
                && !StringUtils.startsWith(clazz.getName(), "java.");
    }

    public static void main(String[] args) {
        Bean reqObj = new Bean();
        Bean reqOb2j = new Bean();
        reqObj.setBean(reqOb2j);
        System.out.println(checkSign(reqObj));
    }

    @Setter
    @Getter
    static class Bean extends BaseVO {

        private Long id = 123L;
        private String sign = "123123";
        private String secretKey = "g1BEQkXHqWj7JLEVOENhpFGmsO6ZZzjo";
        private String i2d;
        private Bean bean;

        private List<Bean> beans = new ArrayList<>();

        private List<String> beanss;
    }

}
