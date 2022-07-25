
package cn.springlet.log.aspect;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.springlet.core.util.IpUtil;
import cn.springlet.core.util.ServletUtil;
import cn.springlet.log.annotation.OptLog;
import cn.springlet.log.bean.OptLogBean;
import cn.springlet.log.bean.OptUserBean;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;


/**
 * 系统日志，切面处理类
 * 部分内容 需要 自主实现
 *
 * @author watermelon
 * @time 2020/10/20
 */
@Slf4j
public abstract class BaseOperationLogAspect {


    @Pointcut("@annotation(cn.springlet.log.annotation.OptLog)")
    public void logPointCut() {

    }

    @Around("logPointCut()&& @annotation(optLog)")
    public Object around(ProceedingJoinPoint point, OptLog optLog) throws Throwable {
        TimeInterval timeInterval = DateUtil.timer();

        OptLogBean optLogBean = new OptLogBean();
        HttpServletRequest request = ServletUtil.getRequest();

        OptUserBean optInfo = getOptUserBean();
        optLogBean.setOptId(optInfo.getOptId());
        optLogBean.setOptAccount(optInfo.getOptAccount());
        optLogBean.setOptName(optInfo.getOptName());

        optLogBean.setOptIp(IpUtil.getClientIp());
        optLogBean.setRequestUrl(request.getRequestURI());
        optLogBean.setRequestMethod(request.getMethod());
        Object[] args = point.getArgs();
        try {
            optLogBean.setRequestParams(JSON.toJSONString(args));
        } catch (Exception e) {
            optLogBean.setRequestParams("error:" + ExceptionUtil.stacktraceToString(e, 350));
        }
        optLogBean.setRemoteAddr(IpUtil.getHostIp());
        optLogBean.setRequestClass(point.getTarget().getClass().getName() + "#" + point.getSignature().getName());
        optLogBean.setOptDesc(optLog.desc());
        optLogBean.setOptType(optLog.type().name());
        optLogBean.setGmtCreate(new Date());
        Object result = null;
        try {
            //执行方法
            result = point.proceed();
        } catch (Exception e) {
            optLogBean.setResponseParams("error:" + ExceptionUtil.stacktraceToString(e, 350));
            optLogBean.setExecTime(timeInterval.interval());
            insert(optLogBean);

            throw e;
        }

        try {
            optLogBean.setResponseParams(JSON.toJSONString(result));
        } catch (Exception e) {
            optLogBean.setResponseParams("error:" + ExceptionUtil.stacktraceToString(e, 350));
        }
        optLogBean.setExecTime(timeInterval.interval());
        insert(optLogBean);
        return result;
    }

    /**
     * 获取操作人信息
     *
     * @return
     */
    protected abstract OptUserBean getOptUserBean();

    /**
     * 插入日志
     *
     * @param optLogBean
     */
    protected abstract void insert(OptLogBean optLogBean);

}
