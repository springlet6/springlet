package cn.springlet.core.auto.log;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.springlet.core.enums.HeaderConstantsEnum;
import cn.springlet.core.util.ServletUtil;
import org.slf4j.MDC;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import javax.servlet.*;
import java.io.IOException;

/**
 * 全局 日志流水号 拦截器
 * 拦截请求 将 全局日志流水号 设置到 MDC
 * 在 logback-spring.xml 中可以通过 %X{参数名} 的方式打印此日志流水号
 *
 * @author watermelon
 * @time 2022/4/11
 */
@Configuration
public class DefaultLogNoFilter implements Filter, Ordered {


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String logNo = ServletUtil.getRequest().getHeader(HeaderConstantsEnum.LOG_NO.name());
        if (StrUtil.isBlank(logNo)) {
            logNo = IdUtil.getSnowflakeNextIdStr();
        }
        MDC.put(HeaderConstantsEnum.LOG_NO.name(), logNo);
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            MDC.clear();
        }
    }

    @Override
    public int getOrder() {
        return -1;
    }
}

