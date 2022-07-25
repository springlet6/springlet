package cn.springlet.core.util;

import java.net.InetAddress;
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
