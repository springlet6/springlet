package cn.springlet.redis.aspect;


import cn.hutool.crypto.SecureUtil;
import cn.springlet.core.enums.HeaderConstantsEnum;
import cn.springlet.core.exception.web_return.RepeatSubmitException;
import cn.springlet.core.util.SPELParserUtils;
import cn.springlet.core.util.ServletUtil;
import cn.springlet.core.util.StrUtil;
import cn.springlet.redis.annotation.NoRepeatSubmit;
import cn.springlet.redis.constant.RedisCacheKey;
import cn.springlet.redis.util.RedisUtil;
import lombok.AllArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 防止重复提交切面
 *
 * @author watermelon
 * @time 2020/11/5
 */
@Aspect
@Component
@AllArgsConstructor
@Order(-1)
public class NoRepeatSubmitAspect {

    private final RedisUtil redisUtil;

    /**
     * 定义点
     */
    @Pointcut("@annotation(cn.springlet.redis.annotation.NoRepeatSubmit)")
    public void pointcut() {
    }

    /**
     * 防止 重复提交处理
     *
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("pointcut() && @annotation(noRepeatSubmit)")
    public Object handler(ProceedingJoinPoint joinPoint, NoRepeatSubmit noRepeatSubmit) throws Throwable {
        String annotationKey = noRepeatSubmit.key();

        String key = getKey(joinPoint, annotationKey);
        if (redisUtil.hasKey(key)) {
            String errMsg = noRepeatSubmit.errMsg();
            if (StrUtil.isBlank(errMsg)) {
                throw new RepeatSubmitException("访问速度过快,请稍后再试！");
            } else {
                throw new RepeatSubmitException(errMsg);
            }
        }
        redisUtil.set(key, "NoRepeatSubmit", noRepeatSubmit.time(), noRepeatSubmit.unit());
        return joinPoint.proceed();
    }

    /**
     * 获取 key
     *
     * @return
     */
    private String getKey(ProceedingJoinPoint joinPoint, String annotationKey) {
        String token = ServletUtil.getRequest().getHeader(HeaderConstantsEnum.Authorization.name());
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        String key = null;
        if (StrUtil.isNotBlank(annotationKey)) {
            String parseValue = SPELParserUtils.parse(((MethodSignature) joinPoint.getSignature()).getMethod(), joinPoint.getArgs(), annotationKey, String.class);
            if (StrUtil.isBlank(parseValue)) {
                key = StrUtil.format("{}{}{}", token, className, methodName);
            } else {
                key = StrUtil.format("{}{}{}{}", token, className, methodName, parseValue);
            }
        } else {
            key = StrUtil.format("{}{}{}", token, className, methodName);
        }

        return RedisCacheKey.NO_REPEAT_SUBMIT_KEY + SecureUtil.md5(key);
    }
}
