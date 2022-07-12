package cn.springlet.fast;

import cn.springlet.core.bean.vo.BaseVO;
import cn.springlet.core.config.fastjson.DatabaseEnumDeserializer;
import cn.springlet.core.config.fastjson.DatabaseEnumSerializer;
import cn.springlet.core.enums.YesNoEnum;
import cn.springlet.crypt.annotation.Decrypt;
import cn.springlet.crypt.annotation.Encrypt;
import com.alibaba.fastjson.annotation.JSONField;
import com.hccake.ballcat.common.desensitize.enums.SlideDesensitizationTypeEnum;
import com.hccake.ballcat.common.desensitize.json.annotation.JsonSlideDesensitize;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author watermelon
 * @time 2022/4/12
 */
@Setter
@Getter
public class Bean extends BaseVO {

    public static final String aaa = "1";

    public static int a = 1;
    public final int a4 = 1;
    public  Long a422 = 1L;
    //@Decrypt
    //@Encrypt
    @JSONField(serializeUsing = DatabaseEnumSerializer.class, deserializeUsing = DatabaseEnumDeserializer.class)
    private YesNoEnum id;

    @JsonSlideDesensitize(type = SlideDesensitizationTypeEnum.PHONE_NUMBER)
    private String id222 = "18782553574L";

    @Decrypt
    @Encrypt
    private String i2d;
    @Decrypt
    @Encrypt
    private Bean bean;

    @Decrypt
    @Encrypt
    private List<Bean> beans;

    @Decrypt
    @Encrypt
    private List<String> beanss;
}
