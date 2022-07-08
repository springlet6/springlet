package cn.springlet.core.util;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import cn.springlet.core.exception.UtilException;
import cn.springlet.core.exception.web_return.ParameterVerificationException;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 客户端工具类
 * 补充{@link cn.hutool.extra.servlet.ServletUtil}
 */
public class ServletUtil extends cn.hutool.extra.servlet.ServletUtil {


    /**
     * 获取request
     */
    public static HttpServletRequest getRequestOrNull() {
        ServletRequestAttributes requestAttributes = getRequestAttributes();
        if (requestAttributes == null) {
            return null;
        }
        return requestAttributes.getRequest();
    }

    /**
     * 获取request
     */
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes requestAttributes = getRequestAttributes();
        if (requestAttributes == null) {
            throw new UtilException("request不存在");
        }
        return requestAttributes.getRequest();
    }

    /**
     * 获取String参数
     */
    public static String getParameter(String name) {
        return getRequest().getParameter(name);
    }

    /**
     * 获取String参数
     */
    public static String getParameter(String name, String defaultValue) {
        return Convert.toStr(getRequest().getParameter(name), defaultValue);
    }

    /**
     * 获取Integer参数
     */
    public static Integer getParameterToInt(String name) {
        return Convert.toInt(getRequest().getParameter(name));
    }

    /**
     * 获取Integer参数
     */
    public static Integer getParameterToInt(String name, Integer defaultValue) {
        return Convert.toInt(getRequest().getParameter(name), defaultValue);
    }


    /**
     * 获取response
     */
    public static HttpServletResponse getResponse() {
        return getRequestAttributes().getResponse();
    }

    /**
     * 获取session
     */
    public static HttpSession getSession() {
        return getRequest().getSession();
    }

    public static ServletRequestAttributes getRequestAttributes() {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return null;
        }
        return (ServletRequestAttributes) attributes;
    }


    /**
     * 获取真实ip
     *
     * @return
     */
    public static String getClientIP() {
        return getClientIP(getRequest());
    }
}
