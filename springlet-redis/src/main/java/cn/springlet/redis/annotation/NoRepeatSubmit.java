package cn.springlet.redis.annotation;


import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * 防止重复提交 注解
 *
 * @author watermelon
 * @time 2020/11/05
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NoRepeatSubmit {

    /**
     * 时间
     * 默认 1s
     *
     * @return
     */
    long time() default 1L;

    /**
     * 时间单位
     * 默认 秒
     *
     * @return
     */
    TimeUnit unit() default TimeUnit.MILLISECONDS;

    /**
     * SPEL 表达式
     *
     * @return
     */
    String key() default "";

    /**
     * 错误返回消息
     *
     * @return
     */
    String errMsg() default "";
}
