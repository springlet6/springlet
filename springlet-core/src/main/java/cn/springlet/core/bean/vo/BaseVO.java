package cn.springlet.core.bean.vo;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * rpc 响应基类
 *
 * @author watermelon
 * @time 2020/11/23
 */
public class BaseVO implements Serializable {

    private static final long serialVersionUID = 2545311527142231608L;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
