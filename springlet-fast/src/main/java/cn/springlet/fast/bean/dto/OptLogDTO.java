package cn.springlet.fast.bean.dto;

import cn.springlet.core.bean.request.BaseRequestDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;


/**
 * <p>
 * sys_opt_logDTO
 * </p>
 *
 * @author springlet
 * @since 2022-07-25
 */
@Setter
@Getter
@Accessors(chain = true)
@ApiModel(description="sys_opt_logDTO")
public class OptLogDTO extends BaseRequestDTO{

private static final long serialVersionUID=1L;

    
    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "主键列表 批量操作时使用")
    private List<Long> idList;
    
        @ApiModelProperty(value = "操作人id")
    private String optId;
    
        @ApiModelProperty(value = "操作人账号")
    private String optAccount;
    
        @ApiModelProperty(value = "操作人名称")
    private String optName;
    
        @ApiModelProperty(value = "操作人ip")
    private String optIp;
    
        @ApiModelProperty(value = "请求地址")
    private String requestUrl;
    
        @ApiModelProperty(value = "请求方式")
    private String requestMethod;
    
        @ApiModelProperty(value = "请求参数")
    private String requestParams;
    
        @ApiModelProperty(value = "返回参数")
    private String responseParams;
    
        @ApiModelProperty(value = "执行时长 ms")
    private Long execTime;
    
        @ApiModelProperty(value = "服务器ip")
    private String remoteAddr;
    
        @ApiModelProperty(value = "请求类方法")
    private String requestClass;
    
        @ApiModelProperty(value = "操作描述")
    private String optDesc;
    
        @ApiModelProperty(value = "操作类型")
    private String optType;
    
        

}
