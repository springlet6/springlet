package cn.springlet.core.exception.web_return;

import cn.springlet.core.enums.ResultCodeEnum;
import cn.springlet.core.util.ExceptionHandlerUtil;
import cn.springlet.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 用于通过抛出异常返回消息
 * 不用于接收其他异常，只接收一个返回消息
 *
 * @author watermelon
 * @time 2021/01/09
 */
@Slf4j
public class ReturnMsgException extends RuntimeException {
    private static final long serialVersionUID = 1L;


    private Integer code = ResultCodeEnum.ERROR.code();

    private String message;

    private Object[] args;


    public ReturnMsgException(String message) {
        this.message = message;
    }

    /**
     * 举个栗子:
     * *  throw new ReturnMsgException("保税订单金额不能超过{}元",2000);
     *
     * @param message
     * @param args
     */
    public ReturnMsgException(String message, Object... args) {
        this.args = args;
        this.message = args == null || args.length == 0 ? message : StrUtil.specialFormat(message, this.getArgs());
    }

    public ReturnMsgException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public ReturnMsgException(Integer code, String message, Object... args) {
        this.code = code;
        this.args = args;
        this.message = args == null || args.length == 0 ? message : StrUtil.specialFormat(message, this.getArgs());
    }

    public void printMsg() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("业务错误统一返回->");
        stringBuilder.append("错误code：");
        stringBuilder.append(this.getCode());
        stringBuilder.append(", ");
        stringBuilder.append("返回信息：");
        stringBuilder.append(this.getMessage());
        stringBuilder.append(", ");
        stringBuilder = ExceptionHandlerUtil.printExceptionLog(stringBuilder, this);

        log.error(stringBuilder.toString());
    }

    @Override
    public String getMessage() {
        return message;
    }


    public Integer getCode() {
        return code;
    }

    public Object[] getArgs() {
        return args;
    }

}
