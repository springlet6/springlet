package cn.springlet.log.aspect;


import cn.springlet.log.annotation.AspectLog;
import cn.springlet.log.bean.HttpLogBean;
import cn.springlet.log.bean.LogBean;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;

/**
 * 方法上的注解
 * 切面日志处理
 *
 * @author watermelon
 * @time 2020/10/20
 */
@Aspect
@Component
public class PrintMethodAnnotationLogAspect extends BasePrintLogAspect {


    /**
     * 注解 定义方法级别切点
     * 切 所有使用 PrintAspectLog 注解的方法
     */
    @Pointcut("@annotation(cn.springlet.log.annotation.AspectLog)")
    public void logMethod() {
    }

    /**
     * 注解 方法级别处理
     *
     * @param joinPoint
     * @param aspectLog
     * @return
     * @throws Throwable
     */
    @Around("logMethod() && @annotation(aspectLog)")
    public Object aroundMethod(ProceedingJoinPoint joinPoint, AspectLog aspectLog) throws Throwable {

        String title = StringUtils.isBlank(aspectLog.title()) ? aspectLog.value() : aspectLog.title();
        return super.logNote(joinPoint, aspectLog.level(), title);
    }

    @Override
    protected LogBean logHandle(ProceedingJoinPoint joinPoint, LogBean logBean) {
        MethodSignature sign = (MethodSignature) joinPoint.getSignature();
        Method method = sign.getMethod();
        AspectLog aspectLog = method.getAnnotation(AspectLog.class);
        if (aspectLog.isHttpRequest()
                || joinPoint.getTarget().getClass().isAnnotationPresent(Controller.class)
                || joinPoint.getTarget().getClass().isAnnotationPresent(RestController.class)) {
            return new HttpLogBean(logBean);
        }
        return logBean;
    }
}
