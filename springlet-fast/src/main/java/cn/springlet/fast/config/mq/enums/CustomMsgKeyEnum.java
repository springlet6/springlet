package cn.springlet.fast.config.mq.enums;


import cn.springlet.rocketmq.enums.MsgKeyEnum;

/**
 * 自定义 msgKey
 *
 * @author watermelon
 * @time 2021/8/25
 */
public enum CustomMsgKeyEnum implements MsgKeyEnum<CustomMsgKeyEnum> {

    LOG("LOG", "日志"),
    ;

    /**
     * 业务类型
     */
    private String businessType;

    /**
     * 描述
     */
    private String desc;


    CustomMsgKeyEnum(String businessType, String desc) {
        this.businessType = businessType;
        this.desc = desc;
    }

    @Override
    public String getBusinessType() {
        return businessType;
    }

    @Override
    public String getDesc() {
        return desc;
    }

}
