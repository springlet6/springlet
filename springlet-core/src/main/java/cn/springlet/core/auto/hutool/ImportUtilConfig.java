package cn.springlet.core.auto.hutool;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 将 hutool 中需要注入的包 import
 *
 * @author watermelon
 * @time 2021/3/30
 */
@Import(cn.hutool.extra.spring.SpringUtil.class)
@Configuration
public class ImportUtilConfig {
}
