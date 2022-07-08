package cn.springlet.core.exception.web_return;

/**
 * {@link cn.springlet.core.bean.web.HttpResult} 断言异常
 */
public class HttpResultAssertException extends ReturnMsgException {
    private static final long serialVersionUID = 1L;

    public HttpResultAssertException(String message) {
        super(message);
    }

    public HttpResultAssertException(String message, Object... args) {
        super(message, args);
    }

    public HttpResultAssertException(Integer code, String message) {
        super(code, message);
    }

    public HttpResultAssertException(Integer code, String message, Object... args) {
        super(code, message, args);
    }
}
