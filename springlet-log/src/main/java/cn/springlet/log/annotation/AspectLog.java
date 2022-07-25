package cn.springlet.log.annotation;

import org.slf4j.event.Level;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Controller;

import java.lang.annotation.*;

/**
 * 自定义打印 日志 输出 入参 和返回结果，方便 debug
 * 因为是基于 aop，所以当前类中的方法调用不会触发日志打印
 *
 * @author watermelon
 * @time 2020/10/20
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AspectLog {

    @AliasFor("title")
    String value() default "";

    /**
     * 日志标题（前缀）
     * 方法或者类的 描述
     * 展示出来是
     * <p>
     * auth-退出登录:调用->参数:[], 方法:com.lhs.cutepet.member.biz.modules.auth.controller.AuthController#logout, 请求接口:/member_address/save_or_update, 请求方式:POST, 来源IP:127.0.0.1
     * auth-退出登录:返回->返回值:{"code":200,"msg":"请求成功","serverTime":"2022-04-07 10:02:14","success":true}, 执行时间:184毫秒
     *
     * @return
     */
    @AliasFor("value")
    String title() default "";

    /**
     * 日志等级
     * 判断 并 输出对应的 等级 日志
     *
     * @return
     */
    Level level() default Level.INFO;


    /**
     * 是否是 http 请求
     * 如果是http请求 就打印对应的 http 参数信息
     * 如果当前类有 {@link Controller} 或者 {@link org.springframework.web.bind.annotation.RestController} 注解 则 自动将  isHttpRequest 设置为 true
     *
     * @return
     */
    boolean isHttpRequest() default false;
}
