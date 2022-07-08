package cn.springlet.core.bean.request;

import cn.springlet.core.util.validate.ValidatorUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * 请求参数基类
 *
 * @author watermelon
 * @time 2020/11/23
 */
public class BaseRequestDTO implements Serializable {
    private static final long serialVersionUID = -2668645275986585374L;


    //注解校验参数
    public void validParams() {
        ValidatorUtils.validate(this);
    }

    //注解之外的校验
    public void otherValid() {

    }

    //注解之外的校验
    public void allValid() {
        validParams();
        otherValid();
    }


    //参数的字符处理
    public void charHandle() {

    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
