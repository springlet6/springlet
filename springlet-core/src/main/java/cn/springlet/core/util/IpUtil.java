package cn.springlet.core.util;

import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.ArrayUtil;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

/**
 * @author watermelon
 * @time 2021/03/09
 */
public class IpUtil {

    private static final String LOOP_BACK = "127.0.0.1";

    public IpUtil() {
    }


    public static String getClientIp() {
        return ServletUtil.getClientIP();
    }


    /**
     * webflux 获取真实ip
     *
     * @param request
     * @return
     */
    public static String getClientIp(ServerHttpRequest request, String... otherHeaderNames) {
        if (request == null) {
            return "未知";
        }
        String[] headerNames = {"X-Forwarded-For", "X-Real-IP", "Proxy-Client-IP", "WL-Proxy-Client-IP", "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR"};
        if (ArrayUtil.isNotEmpty(otherHeaderNames)) {
            headerNames = ArrayUtil.addAll(headerNames, otherHeaderNames);
        }

        String ip;
        for (String header : headerNames) {
            ip = request.getHeaders().getFirst(header);
            if (!NetUtil.isUnknown(ip)) {
                return NetUtil.getMultistageReverseProxyIp(ip);
            }
        }

        InetSocketAddress remoteAddress = request.getRemoteAddress();
        if (remoteAddress == null) {
            return "未知";
        }
        ip = remoteAddress.getAddress().getHostAddress();
        return NetUtil.getMultistageReverseProxyIp(ip);
    }

    public static String getHostIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
        }
        return LOOP_BACK;
    }

    public static String getHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
        }
        return "未知";
    }
}
