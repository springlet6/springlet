package cn.springlet.core.util;

import lombok.Getter;
import lombok.Setter;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 异常处理 util
 *
 * @author watermelon
 * @time 2021/03/09
 */
public class ExceptionHandlerUtil {

    private static Method getStackTraceMethod;
    private static Method getClassNameMethod;
    private static Method getMethodNameMethod;
    private static Method getLineNumberMethod;

    static {
        try {
            Class[] noArgs = null;
            getStackTraceMethod = Throwable.class.getMethod("getStackTrace", noArgs);
            Class stackTraceElementClass = Class.forName("java.lang.StackTraceElement");
            getClassNameMethod = stackTraceElementClass.getMethod("getClassName", noArgs);
            getMethodNameMethod = stackTraceElementClass.getMethod("getMethodName", noArgs);
            getLineNumberMethod = stackTraceElementClass.getMethod("getLineNumber", noArgs);
        } catch (ClassNotFoundException ex) {
        } catch (NoSuchMethodException ex) {
        }

    }

    /**
     * 打印异常日志
     *
     * @param e
     * @return
     */
    public static StringBuilder printExceptionLog(StringBuilder stringBuilder, Exception e) {
        HttpServletRequest request = ServletUtil.getRequest();
        if (stringBuilder == null) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("业务错误统一返回->");
        }

        ExceptionHandlerUtil.SourceInfo exceptionSourceInfo = ExceptionHandlerUtil.getExceptionSourceInfo(e);
        if (exceptionSourceInfo != null) {
            stringBuilder.append("方法:");
            stringBuilder.append(exceptionSourceInfo.getClassName()).append("#").append(exceptionSourceInfo.getMethodName());
            stringBuilder.append(", ");
            stringBuilder.append("行号:");
            stringBuilder.append(exceptionSourceInfo.getLine());
        }

        stringBuilder.append(", ");
        stringBuilder.append("请求接口:");
        stringBuilder.append(request.getRequestURI());
        stringBuilder.append(", ");
        stringBuilder.append("请求方式:");
        stringBuilder.append(request.getMethod());
        stringBuilder.append(", ");
        stringBuilder.append("来源IP:");
        stringBuilder.append(ServletUtil.getClientIP());


        return stringBuilder;
    }


    /**
     * 获取异常来源
     *
     * @param e
     * @return
     */
    public static SourceInfo getExceptionSourceInfo(Exception e) {
        SourceInfo sourceInfo = null;
        try {
            Object[] noArgs = null;
            Object[] elements = (Object[]) getStackTraceMethod.invoke(e, noArgs);
            if (elements.length > 0) {
                String thisClassName = (String) getClassNameMethod.invoke(elements[0], noArgs);
                String methodName = (String) getMethodNameMethod.invoke(elements[0], noArgs);
                int line = ((Integer) getLineNumberMethod.invoke(elements[0], noArgs)).intValue();
                sourceInfo = new SourceInfo();
                sourceInfo.setClassName(thisClassName);
                sourceInfo.setMethodName(methodName);
                sourceInfo.setLine(line);
            }
        } catch (IllegalAccessException e1) {
        } catch (InvocationTargetException e2) {
        }
        return sourceInfo;
    }

    @Setter
    @Getter
    public static class SourceInfo {
        /**
         * 类名
         */
        private String className;
        /**
         * 方法名
         */
        private String methodName;
        /**
         * 行号
         */
        private int line;

    }
}
