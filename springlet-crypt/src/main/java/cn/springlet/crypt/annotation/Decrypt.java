package cn.springlet.crypt.annotation;

import cn.springlet.crypt.enums.Algorithm;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.lang.annotation.*;

/**
 * 解密
 *
 * @author watermelon
 * @time 2020/9/24
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
@Order(Ordered.HIGHEST_PRECEDENCE)
public @interface Decrypt {

    /**
     * 算法
     *
     * @return
     */
    Algorithm algorithm() default Algorithm.RSA;

    /**
     * 私钥
     */
    String privateKey() default "";

    /**
     * 公钥
     */
    String publicKey() default "";
}