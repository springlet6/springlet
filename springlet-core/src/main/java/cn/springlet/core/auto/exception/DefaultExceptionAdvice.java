package cn.springlet.core.auto.exception;


import cn.springlet.core.bean.web.HttpResult;
import cn.springlet.core.enums.ResultCodeEnum;
import cn.springlet.core.exception.web_return.HttpResultAssertException;
import cn.springlet.core.exception.web_return.ParameterVerificationException;
import cn.springlet.core.exception.web_return.ReturnMsgException;
import cn.springlet.core.util.ExceptionHandlerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

/**
 * 全局异常处理器
 * 继承此类
 */
@Slf4j
@RestControllerAdvice
public class DefaultExceptionAdvice {

    /**
     * 异常消息返回处理
     * 不再抛出异常
     */
    @ExceptionHandler(HttpResultAssertException.class)
    public HttpResult HttpResultAssertException(HttpResultAssertException e) {
        e.printMsg();
        log.error("远程调用断言异常", e);
        return HttpResult.error(e.getCode(), e.getMessage());
    }


    /**
     * 自定义验证异常
     */
    @ExceptionHandler(ParameterVerificationException.class)
    public HttpResult parameterVerificationException(ParameterVerificationException e) {
        log.error(e.getMessage(), e);
        String message = e.getMessage();
        return HttpResult.error(message);
    }

    /**
     * 异常消息返回处理
     * 不再抛出异常
     */
    @ExceptionHandler(ReturnMsgException.class)
    public HttpResult returnMsgException(ReturnMsgException e) {
        e.printMsg();
        return HttpResult.error(e.getCode(), e.getMessage());
    }


    /**
     * 自定义验证异常
     */
    @ExceptionHandler(BindException.class)
    public HttpResult validatedBindException(BindException e) {
        log.error(e.getMessage(), e);
        String message = e.getAllErrors().get(0).getDefaultMessage();
        return HttpResult.error(message);
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public HttpResult validExceptionHandler(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        String message = e.getBindingResult().getFieldError().getDefaultMessage();
        return HttpResult.error(message);
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    public HttpResult constraintViolationExceptionHandler(ConstraintViolationException e) {
        log.error(e.getMessage(), e);
        String message = e.getConstraintViolations().iterator().next().getMessage();
        return HttpResult.error(message);
    }

    /**
     * 参数匹配异常
     */
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public HttpResult httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException e) {
        log.error(e.getMessage(), e);
        return HttpResult.error("参数匹配错误！");
    }

    /**
     * 请求方式错误异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public HttpResult HttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        StringBuffer sb = new StringBuffer();
        sb.append("不支持");
        sb.append(e.getMethod());
        sb.append("请求方法,");
        sb.append("支持以下 ");
        String[] methods = e.getSupportedMethods();
        if (methods != null) {
            for (String str : methods) {
                sb.append(str);
                sb.append(",");
            }
        }
        log.error(sb.toString(), e);
        return HttpResult.error(ResultCodeEnum.BAD_METHOD.code(), sb.toString());
    }


    @ExceptionHandler(Exception.class)
    public HttpResult handleException(Exception e) {
        StringBuilder stringBuilder = ExceptionHandlerUtil.printExceptionLog(null, e);

        log.error(stringBuilder.toString());
        log.error(e.getMessage(), e);

        // 发送异常消息
        //return HttpResult.error(e.getMessage());
        return HttpResult.error("服务器异常");
    }
}
