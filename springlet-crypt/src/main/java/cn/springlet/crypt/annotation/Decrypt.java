package cn.springlet.crypt.annotation;

import cn.springlet.crypt.CryptStrategy;
import cn.springlet.crypt.strategy.RSAStrategy;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.lang.annotation.*;

/**
 * 解密
 * 可作用于 String 类型字段  实体  Collection
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