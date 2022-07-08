//package cn.springlet.crypt.aspect;
//
//import cn.springlet.crypt.annotation.Decrypt;
//import cn.springlet.crypt.enums.Algorithm;
//import cn.springlet.crypt.rsa.RSAUtil;
//import org.springframework.core.annotation.AnnotationUtils;
//
//import java.lang.annotation.Annotation;
//import java.lang.reflect.Field;
//import java.lang.reflect.ParameterizedType;
//import java.lang.reflect.Type;
//import java.util.List;
//
///**
// * @author watermelon
// * @since 2022/7/8
// */
//public class BaseCryptAspect {
//    /**
//     * 解密/加密处理
//     */
//    public static <A extends Annotation> void handleItem(Object item, Class<A> annotationType) throws IllegalAccessException {
//        // 捕获类中的所有字段
//        Field[] fields = item.getClass().getDeclaredFields();
//        // 遍历所有字段
//        for (Field field : fields) {
//            // 若该字段被SecurityParameter注解,则进行解密/加密
//            Class<?> fieldType = field.getType();
//            if (fieldType == String.class) {
//                if (null != AnnotationUtils.findAnnotation(field, annotationType)) {
//                    // 设置private类型允许访问
//                    field.setAccessible(Boolean.TRUE);
//                    handleField(item, field, annotationType);
//                    field.setAccessible(Boolean.FALSE);
//                }
//            } else if (List.class.isAssignableFrom(field.getType())) {
//                Type genericType = field.getGenericType();
//                // 是否参数化类型
//                if (genericType instanceof ParameterizedType) {
//                    ParameterizedType pt = (ParameterizedType) genericType;
//                    Class genericClazz = (Class) pt.getActualTypeArguments()[0];
//                    if (genericClazz == String.class && null != AnnotationUtils.findAnnotation(field, SensitiveField.class)) {
//                        field.setAccessible(Boolean.TRUE);
//                        List list = (List) field.get(item);
//                        if (list != null && !list.isEmpty()) {
//                            for (int i = 0; i < list.size(); i++) {
//                                if (isEncrypted) {
//                                    list.set(i, AESUtil.decrypt((String) list.get(i), secretKey));
//                                } else {
//                                    list.set(i, AESUtil.encrypt((String) list.get(i), secretKey));
//                                }
//                            }
//                        }
//                        field.setAccessible(Boolean.FALSE);
//                    } else {
//                        Field[] subFields = genericClazz.getDeclaredFields();
//                        for (Field subField : subFields) {
//                            if (subField.getType() == String.class && null != AnnotationUtils.findAnnotation(subField, SensitiveField.class)) {
//                                field.setAccessible(Boolean.TRUE);
//                                List list = (List) field.get(item);
//                                if (list != null && !list.isEmpty()) {
//                                    for (Object subObj : list) {
//                                        subField.setAccessible(Boolean.TRUE);
//                                        handleField(subObj, subField, secretKey, isEncrypted);
//                                        subField.setAccessible(Boolean.FALSE);
//                                    }
//                                    field.setAccessible(Boolean.FALSE);
//                                }
//                            }
//                        }
//                    }
//                }
//            } else if (fieldType.getName().startsWith("com.jn.ssr")) {
//                Field[] subFields = fieldType.getDeclaredFields();
//                for (Field subField : subFields) {
//                    if (subField.getType() == String.class && null != AnnotationUtils.findAnnotation(subField, SensitiveField.class)) {
//                        field.setAccessible(Boolean.TRUE);
//                        Object obj = field.get(item);
//                        subField.setAccessible(Boolean.TRUE);
//                        handleField(obj, subField, secretKey, isEncrypted);
//                        subField.setAccessible(Boolean.FALSE);
//                        field.setAccessible(Boolean.FALSE);
//                    }
//                }
//            }
//            // T responseData由于泛型擦除，通过反射无法直接获取到实际类型
//            else if (fieldType == Object.class) {
//                field.setAccessible(Boolean.TRUE);
//                handleItem(field.get(item), secretKey, isEncrypted);
//                field.setAccessible(Boolean.FALSE);
//            }
//        }
//    }
//
//    private static <A extends Annotation> void handleField(Object item, Field field, Class<A> annotationType) throws IllegalAccessException {
//        if (null == item) {
//            return;
//        }
//        if (annotationType == Decrypt.class) {
//            Algorithm algorithm = null;
//            switch (algorithm) {
//                case RSA:
//                    if () {
//                        field.set(item, RSAUtil.decrypt());
//                    } else {
//                        field.set(item, RSAUtil.encrypt());
//                    }
//                    break;
//            }
//        } else {
//            field.set(item, AESUtil.encrypt((String) field.get(item), secretKey));
//        }
//    }
//}
