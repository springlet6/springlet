package cn.springlet.fast.bean.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;


/**
 * <p>
 * sys_opt_log
 * </p>
 *
 * @author springlet
 * @since 2022-07-25
 */
@Setter
@Getter
            @Accessors(chain = true)
    @TableName("sys_opt_log")
public class OptLogDO extends Model<OptLogDO> {

private static final long serialVersionUID = 1L;

                            /**
         * 主键
         */
                                    @TableId(value = "id", type = IdType.ASSIGN_ID)
                                        private Long id;


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
                                    @TableField(fill = FieldFill.INSERT)
                                private Date gmtCreate;



@Override
public Serializable pkVal() {
                return this.id;
            }

        }
