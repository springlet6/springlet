package cn.springlet.core.auto.dingtalk;

import cn.hutool.core.util.StrUtil;
import cn.springlet.core.bean.request.BaseRequestDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 钉钉消息
 *
 * @author watermelon
 * @time 2022/4/12
 */
@Setter
@Getter
@Slf4j
public class DingTalkSendMsgDTO extends BaseRequestDTO {

    /**
     * 加签密钥
     */
    private String secret;

    /**
     * Webhook 地址
     */
    private String webhook;

    /**
     * 发送内容
     */
    private String content;

    /**
     * 是否通知所有人
     */
    private Boolean isAtAll;

    /**
     * 通知具体人的手机号码列表
     */
    private List<String> mobileList;

    public boolean valid() {
        if (StrUtil.isBlank(secret)) {
            log.error("钉钉推送消息:加签密钥不能为空");
            return false;
        }
        if (StrUtil.isBlank(webhook)) {
            log.error("钉钉推送消息:Webhook 地址不能为空");
            return false;
        }
        if (StrUtil.isBlank(content)) {
            log.error("钉钉推送消息:发送内容不能为空");
            return false;
        }
        return true;
    }
}
