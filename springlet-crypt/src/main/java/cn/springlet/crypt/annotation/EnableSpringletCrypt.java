package cn.springlet.crypt.annotation;

import cn.springlet.crypt.AutoConfiguration;
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
public @interface EnableSpringletCrypt {
}
