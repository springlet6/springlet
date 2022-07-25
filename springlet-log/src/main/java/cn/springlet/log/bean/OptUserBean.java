package cn.springlet.log.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


/**
 * <p>
 * 操作人信息
 * </p>
 *
 * @author springlet
 * @since 2022-07-22
 */
@Setter
@Getter
@Accessors(chain = true)
public class OptUserBean {

    private static final long serialVersionUID = 1L;
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
}
