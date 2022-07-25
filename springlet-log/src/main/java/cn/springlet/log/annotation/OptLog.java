
package cn.springlet.log.annotation;

import cn.springlet.log.enums.OperationType;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 操作日志,数据库日志
 * 如果用到此注解 需要继承  cn.springlet.log.aspect.OperationLogAspect 并实现其抽象方法
 *
 * @author watermelon
 * @time 2020/10/20
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OptLog {


    @AliasFor("desc")
    String value() default "";

    /**
     * 操作描述
     *
     * @return
     */
    @AliasFor("value")
    String desc() default "";

    /**
     * 类型
     *
     * @return
     */
    OperationType type() default OperationType.OTHER;

}
