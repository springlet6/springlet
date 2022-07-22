package cn.springlet.core.exception.web_return;

import cn.springlet.core.enums.ResultCodeEnum;

/**
 * 数据不存在异常
 */
public class DataDoesNotExistException extends ReturnMsgException {
    private static final long serialVersionUID = 1L;

    public DataDoesNotExistException(String message) {
        super(ResultCodeEnum.DATA_EXISTENT.code(), message);
    }

    public DataDoesNotExistException(String message, Object... args) {
        super(ResultCodeEnum.DATA_EXISTENT.code(), message, args);
    }

    public DataDoesNotExistException(Integer code, String message) {
        super(code, message);
    }

    public DataDoesNotExistException(Integer code, String message, Object... args) {
        super(code, message, args);
    }
}
