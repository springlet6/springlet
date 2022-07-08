package cn.springlet.core.util.validate.constant;

import java.util.regex.Pattern;

/**
 * 正则表达式常量
 *
 * @author watermelon
 * @time 2020/11/26
 */
public class RegexpConstants {
    /**
     * 匹配 大小写字母+数字+汉字
     */
    public static final String NUMBER_LETTER_CHINESE_CHARACTERS = "^[A-Za-z0-9\\u4e00-\\u9fa5]+$";
    public static final String NUMBER_LETTER_CHINESE_CHARACTERS_VALUE = "只能包含大小写字母、数字、汉字";

    /**
     * 匹配 大小写字母+数字
     */
    public static final String NUMBER_LETTER = "^[A-Za-z0-9]+$";
    public static final String NUMBER_LETTER__VALUE = "只能包含大小写字母、数字";


    /**
     * 匹配 数字
     */
    public static final String NUMBER = "^[0-9]+$";
    public static final String NUMBER__VALUE = "只能包含数字";

    /**
     * 匹配 大小写字母
     */
    public static final String LETTER = "^[A-Za-z]+$";
    public static final String LETTER__VALUE = "只能包含大小写字母";

    /**
     * 匹配 大写字母
     */
    public static final String CAPITAL_LETTER = "^[A-Z]+$";
    public static final String CAPITAL_LETTER__VALUE = "只能包含大写字母";

    /**
     * 匹配 小写字母
     */
    public static final String LOWER_LETTER = "^[a-z]+$";
    public static final String LOWER_LETTER__VALUE = "只能包含小写字母";

    /**
     * 匹配手机号
     * 匹配 手机号码
     */
    public static final String PHONE = "1[3|4|5|6|7|8|9][0-9]\\d{8}";
    public static final String PHONE_VALUE = "请输入有效手机号码";

    /**
     * 匹配手机号及座机号(座机号需加区号,-符号可加可不加)
     */
    public static final String PHONELANDLINE = "^((0\\d{2,3}-\\d{6,8})|(0\\d{2,3}\\d{6,8})|1[3|4|5|6|7|8|9][0-9]\\d{8})$";
    public static final String PHONELANDLINE_VALUE = "请输入有效手机号码或座机号";

    /**
     * 匹配 排除虚拟手机号段 的手机号
     * 用于保税时检查 手机号不能为虚拟手机号
     * 虚拟号码段：170、171、165、162、167
     */
    //public static final String VIRTUAL_PHONE = "1[3|4|5|8|9][0-9]\\d{8}|16[0|1|3|4|6|8|9]\\d{8}|17[3|4|5|6|7|8|9]\\d{8}";
    //暂时放开虚拟号码
    public static final String VIRTUAL_PHONE = "1[3|4|5|6|7|8|9][0-9]\\d{8}";
    public static final String VIRTUAL_PHONE_VALUE = "请输入有效手机号码";

    public static void main(String[] args) {
        boolean m0 = Pattern.matches(RegexpConstants.VIRTUAL_PHONE, "17000000000");
        boolean m1 = Pattern.matches(RegexpConstants.VIRTUAL_PHONE, "171000100");
        boolean m2 = Pattern.matches(RegexpConstants.VIRTUAL_PHONE, "16200000000");
        boolean m3 = Pattern.matches(RegexpConstants.VIRTUAL_PHONE, "16500000000");
        boolean m4 = Pattern.matches(RegexpConstants.VIRTUAL_PHONE, "16700000000");
        boolean m5 = Pattern.matches(RegexpConstants.VIRTUAL_PHONE, "16100000000");
        boolean m6 = Pattern.matches(RegexpConstants.VIRTUAL_PHONE, "17300000000");
        boolean m7 = Pattern.matches(RegexpConstants.VIRTUAL_PHONE, "18782553577");
        System.out.println(m0);

        boolean matches1 = Pattern.matches(RegexpConstants.LETTER, "Aa");
        System.out.println(matches1);
    }
}
