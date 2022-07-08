package cn.springlet.core.util;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.IdUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串特殊字符处理
 *
 * @author watermelon
 * @time 2021/3/18
 */
public class StrUtil extends cn.hutool.core.util.StrUtil {

    /**
     * 替换 换行符等特殊字符
     *
     * @return
     */
    public static String replaceSpecialCharacters(String str) {
        if (cn.hutool.core.util.StrUtil.isBlank(str)) {
            return str;
        }
        Pattern p = Pattern.compile("\t|\r|\n|\0| ");
        Matcher m = p.matcher(str);
        return m.replaceAll("");
    }


    /**
     * 格式化 默认加上 [],并在前后加上一个空格
     * <p>
     * StringFormatUtil.specialFormat("名字{}性别{}", "watermelon","男")
     * 输出结果：名字 [watermelon] 性别 [男]
     *
     * @param msg
     * @param args
     * @return
     */
    public static String specialFormat(String msg, Object... args) {
        if (!ArrayUtil.isEmpty(args)) {
            int length = args.length;
            for (int i = 0; i < length; i++) {
                String argStr = args[i].toString();
                args[i] = " [".concat(argStr).concat("] ");
            }

        }
        return format(msg, args);
    }


    public static void main(String[] args) {
        specialFormat("{}dasd{}fsd", 123, 33);
        System.out.println(IdUtil.getSnowflakeNextIdStr());

    }
}
