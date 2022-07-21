package cn.springlet.crypt.annotation;

import cn.springlet.crypt.CryptStrategy;
import cn.springlet.crypt.strategy.RSAStrategy;

import java.lang.annotation.*;

/**
 * 切面 加密解密 字段
 *
 * @author watermelon
 * @time 2020/9/24
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface AsyncCipherText {

    /**
     * 算法策略
     *
     * @return
     */
    Class<? extends CryptStrategy> strategy() default RSAStrategy.class;

    /**
     * 私钥
     */
    String privateKey() default "";

    /**
     * 公钥
     */
    String publicKey() default "";
}