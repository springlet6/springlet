package cn.springlet.core.exception.web_return;

import cn.springlet.core.enums.ResultCodeEnum;

/**
 * 重复提交异常
 */
public class RepeatSubmitException extends ReturnMsgException {
    private static final long serialVersionUID = 1L;

    public RepeatSubmitException(String message) {
        super(ResultCodeEnum.REPEAT_SUBMIT.code(), message);
    }

    public RepeatSubmitException(String message, Object... args) {
        super(ResultCodeEnum.REPEAT_SUBMIT.code(), message, args);
    }

    public RepeatSubmitException(Integer code, String message) {
        super(code, message);
    }

    public RepeatSubmitException(Integer code, String message, Object... args) {
        super(code, message, args);
    }
}
