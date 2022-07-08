package cn.springlet.crypt.annotation;

import java.lang.annotation.*;


/**
 * 加密
 *
 * @author watermelon
 * @time 2020/9/24
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Encrypt {


    /**
     * 设置加密秘钥
     *
     * @return 加密秘钥
     */
    String secret() default "";
}