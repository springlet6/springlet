package cn.springlet.core.exception.web_return;

import cn.springlet.core.enums.ResultCodeEnum;

/**
 * 参数校验异常
 */
public class ParameterVerificationException extends ReturnMsgException {
    private static final long serialVersionUID = 1L;

    public ParameterVerificationException(String message) {
        super(ResultCodeEnum.PARAMS_ERROR.code(), message);
    }

    public ParameterVerificationException(String message, Object... args) {
        super(ResultCodeEnum.PARAMS_ERROR.code(), message, args);
    }

    public ParameterVerificationException(Integer code, String message) {
        super(code, message);
    }

    public ParameterVerificationException(Integer code, String message, Object... args) {
        super(code, message, args);
    }
}
