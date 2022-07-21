package cn.springlet.crypt.aspect;

import cn.hutool.core.lang.Singleton;
import cn.springlet.core.util.StrUtil;
import cn.springlet.crypt.CryptStrategy;
import cn.springlet.crypt.annotation.AsyncCipherText;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 切面 见 {@link cn.springlet.crypt.annotation.JsonCipherText}
 *
 * @author watermelon
 * @time 2020/9/24
 */
//@Component
//@Aspect
@Slf4j
//@Order(-1)
public class CryptAspect {
    /**
     * 切点
     * 仅作用于 {@link org.springframework.stereotype.Controller} 和 {@link org.springframework.web.bind.annotation.RestController} 注解包裹的类中
     */
    @Pointcut("@within(org.springframework.stereotype.Controller) ||@within(org.springframework.web.bind.annotation.RestController)")
    public void point() {

    }

    @Around("point()")
    public Object doProcess(ProceedingJoinPoint joinPoint) throws Throwable {
        List<Object> methodArgs = this.getMethodArgs(joinPoint);
        // 循环所有参数项
        for (Object item : methodArgs) {
            // 对参数项进行敏感字段解密处理
            handleItem(item, false);
        }
        Object result = joinPoint.proceed();
        handleItem(result, true);
        return result;
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

    /**
     * 处理参数 仅 加密/解密 String 类型 支持嵌套
     *
     * @param item
     * @throws IllegalAccessException
     */
    public static void handleItem(Object item, boolean isEncrypt) throws IllegalAccessException {
        if (item == null) {
            return;
        }
        // 捕获类中的所有字段
        Field[] fields = item.getClass().getDeclaredFields();
        // 遍历所有字段
        for (Field field : fields) {
            Class<?> fieldType = field.getType();
            if (fieldType == String.class) {
                // 设置private类型允许访问
                itemOpt(item, field, isEncrypt);
                continue;
            }
            if (Collection.class.isAssignableFrom(field.getType())) {
                Type genericType = field.getGenericType();
                // 是否参数化类型
                if (genericType instanceof ParameterizedType) {
                    ParameterizedType pt = (ParameterizedType) genericType;
                    Class genericClazz = (Class) pt.getActualTypeArguments()[0];
                    field.setAccessible(Boolean.TRUE);
                    List list = (List) field.get(item);
                    if (list == null) {
                        continue;
                    }
                    if (genericClazz == String.class) {
                        int size = list.size();
                        for (int i = 0; i < size; i++) {
                            Object o = list.get(i);
                            String str = null;
                            if (o == null || StrUtil.isBlank(str = (String) o)) {
                                continue;
                            }
                            if (isEncrypt) {
                                AsyncCipherText encrypt = AnnotationUtils.findAnnotation(field, AsyncCipherText.class);
                                if (null != encrypt) {
                                    CryptStrategy service = Singleton.get(encrypt.strategy());
                                    String privateKey = encrypt.privateKey();
                                    String publicKey = encrypt.publicKey();
                                    if (StrUtil.isNotBlank(privateKey) && StrUtil.isNotBlank(publicKey)) {
                                        list.set(i, service.encrypt(str, privateKey, publicKey));
                                    } else {
                                        list.set(i, service.encrypt(str));
                                    }
                                }
                            } else {
                                AsyncCipherText decrypt = AnnotationUtils.findAnnotation(field, AsyncCipherText.class);
                                if (null != decrypt) {
                                    CryptStrategy strategy = Singleton.get(decrypt.strategy());
                                    String privateKey = decrypt.privateKey();
                                    String publicKey = decrypt.publicKey();
                                    if (StrUtil.isNotBlank(privateKey) && StrUtil.isNotBlank(publicKey)) {
                                        list.set(i, strategy.decrypt(str, privateKey, publicKey));
                                    } else {
                                        list.set(i, strategy.decrypt(str));
                                    }
                                }
                            }
                        }
                        field.setAccessible(Boolean.FALSE);
                    } else {
                        int size = list.size();
                        for (int i = 0; i < size; i++) {
                            handleItem(list.get(i), isEncrypt);
                        }
                    }
                }
                continue;
            }

            if (isNotGeneralType(fieldType) && !Modifier.isStatic(field.getModifiers())) {
                field.setAccessible(Boolean.TRUE);
                handleItem(field.get(item), isEncrypt);
                field.setAccessible(Boolean.FALSE);
            }
        }
    }

    private static void itemOpt(Object item, Field field, boolean isEncrypt) throws IllegalAccessException {
        if (isEncrypt) {
            AsyncCipherText encrypt = AnnotationUtils.findAnnotation(field, AsyncCipherText.class);
            if (null != encrypt) {
                field.setAccessible(Boolean.TRUE);
                handleFieldEncrypt(item, field, encrypt);
                field.setAccessible(Boolean.FALSE);
            }
        } else {
            AsyncCipherText decrypt = AnnotationUtils.findAnnotation(field, AsyncCipherText.class);
            if (null != decrypt) {
                field.setAccessible(Boolean.TRUE);
                handleFieldDecrypt(item, field, decrypt);
                field.setAccessible(Boolean.FALSE);
            }
        }
    }

    private static void handleFieldEncrypt(Object item, Field field, AsyncCipherText encrypt) throws IllegalAccessException {

        String str = (String) field.get(item);
        if (StrUtil.isBlank(str)) {
            return;
        }
        CryptStrategy strategy = Singleton.get(encrypt.strategy());
        String privateKey = encrypt.privateKey();
        String publicKey = encrypt.publicKey();

        if (StrUtil.isNotBlank(privateKey) && StrUtil.isNotBlank(publicKey)) {
            field.set(item, strategy.encrypt(str, privateKey, publicKey));
            return;
        }
        field.set(item, strategy.encrypt(str));
    }

    @SneakyThrows
    private static void handleFieldDecrypt(Object item, Field field, AsyncCipherText decrypt) {
        if (null == item) {
            return;
        }
        String cipherStr = (String) field.get(item);
        if (StrUtil.isBlank(cipherStr)) {
            return;
        }

        CryptStrategy strategy = Singleton.get(decrypt.strategy());
        String privateKey = decrypt.privateKey();
        String publicKey = decrypt.publicKey();

        if (StrUtil.isNotBlank(privateKey) && StrUtil.isNotBlank(publicKey)) {
            field.set(item, strategy.decrypt(cipherStr, privateKey, publicKey));
            return;
        }
        field.set(item, strategy.decrypt(cipherStr));
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
}