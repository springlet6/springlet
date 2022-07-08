package cn.springlet.fast;

import cn.springlet.core.bean.vo.BaseVO;
import cn.springlet.crypt.annotation.Decrypt;
import lombok.Getter;
import lombok.Setter;

/**
 * @author watermelon
 * @time 2022/4/12
 */
@Setter
@Getter
public class Bean extends BaseVO {
    private Long id;
    @Decrypt
    private String i2d;
}
