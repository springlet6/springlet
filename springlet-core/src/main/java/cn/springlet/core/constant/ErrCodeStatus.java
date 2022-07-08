package cn.springlet.core.constant;

/**
 * 返回状态码
 */
public class ErrCodeStatus {
    /**
     * 操作成功
     */
    public static final int SUCCESS = 200;

    /**
     * 没有访问权限
     */
    public static final int FORBIDDEN = 403;

    /**
     * 用户未登录
     */
    public static final int UNAUTHORIZED = 401;

    /**
     * 不允许的http方法
     */
    public static final int BAD_METHOD = 405;

    /**
     * 系统内部错误
     */
    public static final int ERROR = 500;

    /**
     * 参数异常
     */
    public static final int PARAMS_ERROR = 501;

}
