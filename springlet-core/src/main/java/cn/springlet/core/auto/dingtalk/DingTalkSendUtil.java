package cn.springlet.core.auto.dingtalk;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.springlet.core.util.IpUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.core.env.Environment;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static cn.hutool.extra.spring.SpringUtil.getBean;

/**
 * 钉钉消息 util
 *
 * @author watermelon
 * @time 2022/4/12
 */
@Slf4j
public class DingTalkSendUtil {


    /**
     * 发送错误消息
     *
     * @param message
     * @param secret
     * @param webhook
     */
    public static void sendMsg(String message, String secret, String webhook) {

        DingTalkSendMsgDTO dto = new DingTalkSendMsgDTO();
        dto.setSecret(secret);
        dto.setWebhook(webhook);
        dto.setContent(message);
        sendDingTalkMsg(dto);
    }

    /**
     * 默认推送 异常信息
     * 需要配置
     * cutepet.dingtalk.error.secret
     * cutepet.dingtalk.error.webhook
     * 将推送 ip + 系统名 + message
     *
     * @param message
     */
    public static void sendErrorMsgDefault(String message) {
        DingTalkSendMsgDTO dto = new DingTalkSendMsgDTO();
        DingTalkProperties dingTalkProperties = getBean(DingTalkProperties.class);
        dto.setSecret(dingTalkProperties.getSecret());
        dto.setWebhook(dingTalkProperties.getWebhook());
        dto.setContent(buildErrMsg(message));
        sendDingTalkMsg(dto);
    }


    /**
     * 发送钉钉消息 util
     *
     * @param requestDTO
     */
    public static void sendDingTalkMsg(DingTalkSendMsgDTO requestDTO) {
        //参数校验不通过，直接返回
        if (!requestDTO.valid()) {
            return;
        }
        log.debug("sendDingTalkMsg:钉钉推送消息->请求参数{}", requestDTO);

        //消息内容
        Map<String, String> contentMap = new HashMap<>();
        contentMap.put("content", requestDTO.getContent());
        //通知人
        Map<String, Object> atMap = new HashMap<>();
        //1.是否通知所有人
        atMap.put("isAtAll", requestDTO.getIsAtAll());
        //2.通知具体人的手机号码列表
        atMap.put("atMobiles", requestDTO.getMobileList());
        Map<String, Object> reqMap = new HashMap<>();
        reqMap.put("msgtype", "text");
        reqMap.put("text", contentMap);
        reqMap.put("at", atMap);
        requestDTO.setContent(JSON.toJSONString(reqMap));

        try {
            String s = send(requestDTO);
            if (StrUtil.isNotBlank(s)) {
                if (!(JSON.parseObject(s).getInteger("errcode") == 0)) {
                    log.error("钉钉推送消息出现异常:{}", JSON.parseObject(s).getString("errmsg"));
                }
            } else {
                log.error("钉钉推送消息出现异常");
            }
        } catch (Exception e) {
            log.error("钉钉推送消息出现异常", e);
        }
    }

    private static String send(DingTalkSendMsgDTO requestDTO) {
        try {
            String secret = requestDTO.getSecret();
            //获取系统时间戳
            long timestamp = Instant.now().toEpochMilli();
            //拼接
            String stringToSign = timestamp + "\n" + secret;
            //使用HmacSHA256算法计算签名
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256"));
            byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
            //进行Base64 encode 得到最后的sign，可以拼接进url里
            String sign = URLEncoder.encode(new String(Base64.encodeBase64(signData)), "UTF-8");
            //钉钉机器人地址（配置机器人的webhook）
            String dingUrl = requestDTO.getWebhook() + "&timestamp=" + timestamp + "&sign=" + sign;

            String result = HttpUtil.post(dingUrl, requestDTO.getContent());
            log.debug("sendDingTalkMsg:钉钉推送消息->响应报文：{}", result);
            return result;
        } catch (Exception e) {
            log.error("钉钉推送消息出现异常", e);
            return null;
        }
    }


    /**
     * 提供简易的模板
     *
     * @param message
     * @return
     */
    public static String buildErrMsg(String message) {
        return "系统名: " + getBean(Environment.class).getProperty("spring.application.name") + "\n"
                + "ip: " + IpUtil.getHostIp() + "\n"
                + "错误: " + message;
    }

    public static void main(String[] args) {
        DingTalkSendMsgDTO dto = new DingTalkSendMsgDTO();
        dto.setSecret("cykEutF8PUrCdl-weHT9mZJPGNEhngdcFEXXs5zY5ubIovPHyAZu1IJemeEwoboS");
        dto.setWebhook("https://oapi.dingtalk.com/robot/send?access_token=ceaca33fb901d4820305fa7d14e180b64eb6cbbf5cfa9081275bcc3624ce7458");
        dto.setContent("测试");

        sendDingTalkMsg(dto);
    }
}
