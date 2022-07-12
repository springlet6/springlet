package cn.springlet.core.enums;

/**
 * 响应code 枚举
 *
 * @author watermelon
 * @since 2022/7/12
 */
public interface BaseResultCodeEnum {

    /**
     * code
     *
     * @return
     */
    Integer code();

    /**
     * msg
     *
     * @return
     */
    String msg();
}
