package cn.springlet.core.enums;

/**
 * 响应 code
 *
 * @author watermelon
 * @since 2022/7/12
 */
public enum ResultCodeEnum implements BaseResultCodeEnum {

    SUCCESS(200, "请求成功"),
    UNAUTHORIZED(401, "用户未登录"),
    DISABLED(402, "用户已被禁用"),
    FORBIDDEN(403, "没有访问权限"),
    NON_EXISTENT(404, "用户不存在"),

    BAD_METHOD(405, "不允许的http方法"),

    ERROR(500, "系统内部错误"),
    ERROR_REQUEST(500, "请求失败"),
    PARAMS_ERROR(501, "参数异常"),


    ;

    ResultCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private Integer code;

    private String msg;

    @Override
    public Integer code() {
        return code;
    }

    @Override
    public String msg() {
        return msg;
    }
}
