package cn.springlet.core.exception.web_return;

import cn.springlet.core.enums.ResultCodeEnum;

/**
 * rocketmq 异常
 */
public class RocketMqException extends ReturnMsgException {
    private static final long serialVersionUID = 1L;

    public RocketMqException(String message) {
        super(ResultCodeEnum.REPEAT_SUBMIT.code(), message);
    }

    public RocketMqException(String message, Object... args) {
        super(ResultCodeEnum.REPEAT_SUBMIT.code(), message, args);
    }

    public RocketMqException(Integer code, String message) {
        super(code, message);
    }

    public RocketMqException(Integer code, String message, Object... args) {
        super(code, message, args);
    }
}
