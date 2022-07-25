package cn.springlet.rocketmq.annotation;

import cn.springlet.rocketmq.AutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 自动配置
 *
 * @author watermelon
 * @time 2020/9/24
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(AutoConfiguration.class)
public @interface EnableSpringletRocketMq {
}
