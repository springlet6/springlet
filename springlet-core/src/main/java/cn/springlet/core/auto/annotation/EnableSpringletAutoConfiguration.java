package cn.springlet.core.auto.annotation;

import cn.springlet.core.auto.AutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 自动配置
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(AutoConfiguration.class)
public @interface EnableSpringletAutoConfiguration {
}
