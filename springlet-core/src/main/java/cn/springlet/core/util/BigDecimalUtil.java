package cn.springlet.core.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;

/**
 * @author watermelon
 * @time 2021/10/13
 */
public class BigDecimalUtil {

    /**
     * 获取 百分比
     *
     * @param one 被除数
     * @param two 除数
     * @return
     */
    public static String getPercent(BigDecimal one, BigDecimal two) {
        if (one == null || two == null) {
            return "0%";
        }
        if (two.equals(BigDecimal.ZERO)) {
            return "0%";
        }
        BigDecimal divide = one.divide(two, 4, RoundingMode.HALF_UP);

        //将结果百分比
        NumberFormat percent = NumberFormat.getPercentInstance();
        percent.setMaximumFractionDigits(2);
        return percent.format(divide.doubleValue());
    }
}
