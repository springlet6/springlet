package cn.springlet.core.enums;

/**
 * 服务间使用的 header常量
 */
public enum HeaderConstantsEnum {
    //真实请求ip
    REAL_IP,
    //用户编号
    MEMBER_NO,
    //用户id
    MEMBER_ID,
    //客户端类型 pc 小程序  使用枚举维护
    CLIENT_TYPE,
    // token
    Authorization,
    // log_no 全局日志编号
    LOG_NO,
    //compensate token
    COMPENSATE_TOKEN
}