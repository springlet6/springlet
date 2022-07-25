package cn.springlet.log.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;


/**
 * <p>
 * OptLogBean
 * </p>
 *
 * @author springlet
 * @since 2022-07-22
 */
@Setter
@Getter
@Accessors(chain = true)
public class OptLogBean {

    private static final long serialVersionUID = 1L;
    /**
     * 操作人id
     */
    private String optId;


    /**
     * 操作人账号
     */
    private String optAccount;


    /**
     * 操作人名称
     */
    private String optName;


    /**
     * 操作人ip
     */
    private String optIp;


    /**
     * 请求地址
     */
    private String requestUrl;


    /**
     * 请求方式
     */
    private String requestMethod;


    /**
     * 请求参数
     */
    private String requestParams;


    /**
     * 返回参数
     */
    private String responseParams;


    /**
     * 执行时长 ms
     */
    private Long execTime;


    /**
     * 服务器ip
     */
    private String remoteAddr;


    /**
     * 请求类方法
     */
    private String requestClass;


    /**
     * 操作描述
     */
    private String optDesc;


    /**
     * 操作类型
     */
    private String optType;


    /**
     * 创建时间
     */
    private Date gmtCreate;
}
