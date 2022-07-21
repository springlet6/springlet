package cn.springlet.fast.config.enums;

import cn.springlet.core.bean.web.HttpResult;
import cn.springlet.core.util.EnumUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author watermelon
 * @time 2020/6/30
 */
@RequestMapping("/anon")
@Validated
@Api(tags = "anon-枚举相关")
@Slf4j
@RestController
public class DefaultEnumController {


    /**
     * 获取枚举
     *
     * @return
     */
    @GetMapping("/getEnum/{className:[a-zA-Z0-9\\_\\.\\$]+}")
    @ApiOperation("获取枚举详情")
    @ResponseBody
    public HttpResult getEnum(@PathVariable @ApiParam("枚举全限定类名") String className) {
        List<Map<String, Object>> allEnumByClassName = EnumUtils.getAllEnumByClassName(className);
        if (allEnumByClassName == null) {
            return HttpResult.error("枚举不存在");
        }
        return HttpResult.success(allEnumByClassName);
    }
}
