package cn.springlet.fast.test;

import cn.springlet.core.bean.page.PageInfo;
import cn.springlet.core.bean.web.HttpResult;
import cn.springlet.fast.bean.dto.query.OptLogQuery;
import cn.springlet.fast.bean.entity.DemoDO;
import cn.springlet.fast.bean.entity.OptLogDO;
import cn.springlet.fast.bean.vo.OptLogVO;
import cn.springlet.mybatisplus.page.PageResult;
import cn.springlet.mybatisplus.page.PageUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import icu.mhb.mybatisplus.plugln.core.JoinLambdaWrapper;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 内部方法测试
 *
 * @author watermelon
 * @time 2022/4/20
 */
@RestController
@RequestMapping("/")
@AllArgsConstructor
@Slf4j
public class TestController {


    @GetMapping(value = "/")
    public Bean test(@RequestBody Bean bean) {
        String s = JSON.toJSONString(bean);
        Bean bean1 = JSON.parseObject(s, Bean.class);

        log.info("{}", bean);
        return bean;
    }


    //@PostMapping("/page_list")
    //@ApiOperation("sys_opt_log 分页列表")
    //public HttpResult<PageInfo<OptLogVO>> pageList(@RequestBody @Validated OptLogQuery query) {
    //    String keys = query.keysTrim();
    //
    //    JoinLambdaWrapper<OptLogDO> joinLambdaWrapper = new JoinLambdaWrapper<>(OptLogDO.class);
    //
    //    joinLambdaWrapper.leftJoin(DemoDO.class, DemoDO::getPhone, OptLogDO::getOptAccount)
    //            .eq(DemoDO::getPhone, "123")
    //            .selectAs(DemoDO::getPhone, "phone")
    //            .selectAs(DemoDO::getPhone, "phone")
    //            .select()
    //            .end()
    //            .orderByDesc(OptLogDO::getGmtCreate)
    //            .orderByDesc(OptLogDO::getId);
    //
    //    IPage<OptLogVO> page = optLogCrud.joinPage(PageUtil.getMpPage(query), joinLambdaWrapper, OptLogVO.class);
    //
    //    return PageResult.httpSuccess(page);
    //}

}
