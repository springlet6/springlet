package cn.springlet.log.aspect;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.springlet.core.bean.page.PageInfo;
import cn.springlet.core.bean.web.HttpResult;
import cn.springlet.log.bean.LogBean;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.event.Level;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * 日志打印 基类
 * 提供日志打印具体实现
 * 可自定义切点 实现此基类
 *
 * @author watermelon
 * @time 2020/10/20
 */
@Slf4j
public class BasePrintLogAspect {

    public static final String SERVICE_TITLE = "服务";
    public static final String HTTP_TITLE = "http请求";


    /**
     * 具体 日志 打印流程
     *
     * @param joinPoint
     * @param level
     * @param title
     * @return
     * @throws Throwable
     */
    protected Object logNote(ProceedingJoinPoint joinPoint, Level level, String title) throws Throwable {
        //如果日志等级不够，则直接返回
        if (!checkLogLevel(level)) {
            return joinPoint.proceed();
        }
        LogBean logBean = new LogBean();
        //全局logNo
        logBean.setTitle(title);
        if (StrUtil.isBlank(title)) {
            logBean.setTitle(SERVICE_TITLE);
        }
        logBean.setClassName(joinPoint.getTarget().getClass().getName());
        logBean.setMethodName(joinPoint.getSignature().getName());
        try {
            logBean.setParameter(JSON.toJSONString(joinPoint.getArgs()));
        } catch (Exception e) {
            log.info("BasePrintLogAspect:logNote:切面日志异常->JSON序列化参数异常:{}", e.getMessage());
        }
        logBean.setStartTime(LocalDateTime.now());

        LogBean otherLogBean = logHandle(joinPoint, logBean);
        //参数打印
        printParams(otherLogBean, level);
        Object proceed = null;
        try {
            proceed = joinPoint.proceed();
        } catch (Exception e) {
            log.info("BasePrintLogAspect:logNote:切面日志异常->执行过程被异常中断,拦截到返回信息:{}", e.getMessage());
            otherLogBean.setTitle(otherLogBean.getTitle().substring(0, otherLogBean.getTitle().length() - 3));
            otherLogBean.setEndTime(LocalDateTime.now());
            otherLogBean.setReturnObject(e.getMessage());
            //返回结果打印
            printReturn(otherLogBean, level);

            throw e;
        }

        otherLogBean.setTitle(otherLogBean.getTitle().substring(0, otherLogBean.getTitle().length() - 3));
        otherLogBean.setEndTime(LocalDateTime.now());
        try {
            setReturnObject(proceed, otherLogBean);
        } catch (Exception e) {
            log.info("BasePrintLogAspect:logNote:切面日志异常->JSON序列化返回值异常:{}", e.getMessage());
        }
        //返回结果打印
        printReturn(otherLogBean, level);

        return proceed;
    }

    private void setReturnObject(Object proceed, LogBean otherLogBean) {
        if (proceed instanceof HttpResult) {
            HttpResult httpResult = (HttpResult) proceed;
            Object data = httpResult.getData();
            if (data instanceof Collection) {
                Collection collection = (Collection) data;
                if (CollUtil.isNotEmpty(collection)) {
                    otherLogBean.setReturnObject("返回信息为集合，集合大小：" + collection.size());
                } else {
                    otherLogBean.setReturnObject("返回信息为空集合");
                }
                return;
            }
            if (data instanceof PageInfo) {
                Collection collection = ((PageInfo) data).getRecords();
                if (CollUtil.isNotEmpty(collection)) {
                    otherLogBean.setReturnObject("返回信息为集合，集合大小：" + collection.size());
                } else {
                    otherLogBean.setReturnObject("返回信息为空集合");
                }
                return;
            }
        }
        otherLogBean.setReturnObject(JSON.toJSONString(proceed));
    }

    /**
     * 额外的日志处理
     * 如果不做任何处理可以直接返回
     *
     * @param joinPoint
     * @param logBean
     */
    protected LogBean logHandle(ProceedingJoinPoint joinPoint, LogBean logBean) {
        return logBean;
    }


    /**
     * 参数打印
     */
    protected void printParams(LogBean logBean, Level level) {
        logBean.setTitle(logBean.getTitle() + ":调用");
        printLog(logBean, level);
    }

    /**
     * 返回结果打印
     *
     * @param logBean
     */
    protected void printReturn(LogBean logBean, Level level) {
        logBean.setTitle(logBean.getTitle() + ":返回");
        logBean.closeSomeLog();
        printLog(logBean, level);
    }

    /**
     * 输出日志
     *
     * @param logBean
     */
    protected void printLog(LogBean logBean, Level level) {
        switch (level) {
            case TRACE:
                log.trace(logBean.toString());
                break;
            case DEBUG:
                log.debug(logBean.toString());
                break;
            case INFO:
                log.info(logBean.toString());
                break;
            case WARN:
                log.warn(logBean.toString());
                break;
            case ERROR:
                log.error(logBean.toString());
                break;
            default:
        }
    }

    /**
     * 判断日志等级
     *
     * @param level
     * @return
     */
    private boolean checkLogLevel(Level level) {
        switch (level) {
            case TRACE:
                if (log.isTraceEnabled()) {
                    return true;
                }
                break;
            case DEBUG:
                if (log.isDebugEnabled()) {
                    return true;
                }
                break;
            case INFO:
                if (log.isInfoEnabled()) {
                    return true;
                }
                break;
            case WARN:
                if (log.isWarnEnabled()) {
                    return true;
                }
                break;
            case ERROR:
                if (log.isErrorEnabled()) {
                    return true;
                }
                break;
            default:
        }
        return false;
    }


}
