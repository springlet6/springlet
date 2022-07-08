package cn.springlet.crypt.aspect;

import cn.springlet.core.util.StrUtil;
import cn.springlet.crypt.annotation.Decrypt;
import cn.springlet.crypt.enums.Algorithm;
import cn.springlet.crypt.rsa.RSAUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 解密切面
 *
 * @author watermelon
 * @time 2020/9/24
 */
@Component
@Aspect
@Slf4j
public class DecryptAspect {

    /**
     * 切点
     */
    @Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping)")
    public void point() {

    }

    @Around("point()")
    public Object doProcess(ProceedingJoinPoint joinPoint) throws Throwable {
        List<Object> methodArgs = this.getMethodArgs(joinPoint);
        // 循环所有参数项
        for (Object item : methodArgs) {
            // 对参数项进行敏感字段解密处理
            handleItem(item);
        }
        return joinPoint.proceed();
    }

    /**
     * 获取方法的请求参数
     */
    private List<Object> getMethodArgs(ProceedingJoinPoint proceedingJoinPoint) {
        List<Object> methodArgs = new ArrayList<>();
        for (Object arg : proceedingJoinPoint.getArgs()) {
            if (null != arg) {
                methodArgs.add(arg);
            }
        }
        return methodArgs;
    }


    public static <A extends Annotation> void handleItem(Object item) throws IllegalAccessException {
        // 捕获类中的所有字段
        Field[] fields = item.getClass().getDeclaredFields();
        // 遍历所有字段
        for (Field field : fields) {
            // 若该字段被SecurityParameter注解,则进行解密/加密
            Class<?> fieldType = field.getType();
            if (fieldType == String.class) {
                Decrypt annotation = AnnotationUtils.findAnnotation(field, Decrypt.class);
                if (null != annotation) {
                    // 设置private类型允许访问
                    field.setAccessible(Boolean.TRUE);
                    handleField(item, field, annotation);
                    field.setAccessible(Boolean.FALSE);
                }
            } else if (List.class.isAssignableFrom(field.getType())) {
                Type genericType = field.getGenericType();
                // 是否参数化类型
                if (genericType instanceof ParameterizedType) {
                    ParameterizedType pt = (ParameterizedType) genericType;
                    Class genericClazz = (Class) pt.getActualTypeArguments()[0];
                    if (genericClazz == String.class && null != AnnotationUtils.findAnnotation(field, Decrypt.class)) {
                        field.setAccessible(Boolean.TRUE);
                        List list = (List) field.get(item);
                        if (list != null && !list.isEmpty()) {
                            for (int i = 0; i < list.size(); i++) {
                                //list.set(i, AESUtil.encrypt((String) list.get(i), secretKey));
                            }
                        }
                        field.setAccessible(Boolean.FALSE);
                    } else {
                        Field[] subFields = genericClazz.getDeclaredFields();
                        for (Field subField : subFields) {
                            Decrypt annotationType = AnnotationUtils.findAnnotation(subField, Decrypt.class);
                            if (subField.getType() == String.class && null != annotationType) {
                                field.setAccessible(Boolean.TRUE);
                                List list = (List) field.get(item);
                                if (list != null && !list.isEmpty()) {
                                    for (Object subObj : list) {
                                        subField.setAccessible(Boolean.TRUE);
                                        handleField(subObj, subField, annotationType);
                                        subField.setAccessible(Boolean.FALSE);
                                    }
                                    field.setAccessible(Boolean.FALSE);
                                }
                            }
                        }
                    }
                }
            } else if (fieldType.getName().startsWith("com.jn.ssr")) {
                Field[] subFields = fieldType.getDeclaredFields();
                for (Field subField : subFields) {
                    Decrypt annotation = AnnotationUtils.findAnnotation(subField, Decrypt.class);
                    if (subField.getType() == String.class && null != annotation) {
                        field.setAccessible(Boolean.TRUE);
                        Object obj = field.get(item);
                        subField.setAccessible(Boolean.TRUE);
                        handleField(obj, subField, annotation);
                        subField.setAccessible(Boolean.FALSE);
                        field.setAccessible(Boolean.FALSE);
                    }
                }
            }
            // T responseData由于泛型擦除，通过反射无法直接获取到实际类型
            else if (fieldType == Object.class) {
                field.setAccessible(Boolean.TRUE);
                handleItem(field.get(item));
                field.setAccessible(Boolean.FALSE);
            }
        }
    }

    private static <A extends Annotation> void handleField(Object item, Field field, Decrypt annotationType) throws IllegalAccessException {
        if (null == item) {
            return;
        }
        Algorithm algorithm = annotationType.algorithm();
        String privateKey = annotationType.privateKey();
        String publicKey = annotationType.publicKey();

        switch (algorithm) {
            case RSA:
                if (StrUtil.isNotBlank(privateKey) && StrUtil.isNotBlank(publicKey)) {
                    field.set(item, RSAUtil.decrypt((String) field.get(item), privateKey, publicKey));
                } else {
                    field.set(item, RSAUtil.decrypt((String) field.get(item)));
                }
                break;
        }

    }
}