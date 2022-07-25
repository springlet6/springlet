package cn.springlet.fast.controller;

import cn.hutool.core.collection.CollUtil;
import cn.springlet.core.bean.page.PageInfo;
import cn.springlet.core.bean.web.HttpResult;
import cn.springlet.core.util.BeanUtil;
import cn.springlet.fast.bean.dto.OptLogDTO;
import cn.springlet.fast.bean.dto.query.OptLogQuery;
import cn.springlet.fast.bean.entity.OptLogDO;
import cn.springlet.fast.bean.vo.OptLogVO;
import cn.springlet.fast.crud.OptLogCrud;
import cn.springlet.log.annotation.AspectLog;
import cn.springlet.mybatisplus.page.PageResult;
import cn.springlet.mybatisplus.page.PageUtil;
import cn.springlet.redis.util.RedisUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * sys_opt_log controller
 * </p>
 *
 * @author springlet
 * @since 2022-07-25
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/opt_log")
@Api(tags = "sys_opt_log")
public class OptLogController {

    private final OptLogCrud optLogCrud;
    private final RedisUtil redisUtil;

    @PostMapping("/page_list")
    @ApiOperation("sys_opt_log 分页列表")
    public HttpResult<PageInfo<OptLogVO>> pageList(@RequestBody @Validated OptLogQuery query) {
        String keys = query.keysTrim();

        redisUtil.set("123", "321");
        String o = redisUtil.get("123");
        redisUtil.del("123");

        Page<OptLogDO> doPage = optLogCrud.page(PageUtil.getMpPage(query), Wrappers.<OptLogDO>lambdaQuery()
                .orderByDesc(OptLogDO::getGmtCreate)
                .orderByDesc(OptLogDO::getId));

        List<OptLogVO> vos = BeanUtil.copyList(doPage.getRecords(), OptLogVO.class);
        return PageResult.httpSuccess(doPage, vos);
    }

    @Transactional
    @PostMapping("/save_or_update")
    @AspectLog("sys_opt_log-新增或修改")
    @ApiOperation("sys_opt_log-新增或修改")
    public HttpResult<OptLogVO> addOrUpdate(@RequestBody @Validated OptLogDTO dto) {

        OptLogDO entity = BeanUtil.toBean(dto, OptLogDO.class);

        if (entity.getId() == null) {
            //add
            log.info("sys_opt_log-新增");
            entity.insert();
            return HttpResult.success(BeanUtil.toBean(entity, OptLogVO.class));
        } else {
            //update
            log.info("sys_opt_log-修改");
            entity.update(Wrappers.<OptLogDO>lambdaUpdate()
                    //加上数据权限条件 比如用户编号
                    .eq(OptLogDO::getId, entity.getId()));
        }

        return HttpResult.success(BeanUtil.toBean(entity, OptLogVO.class));
    }

    @Transactional
    @PostMapping("/delete")
    @AspectLog("sys_opt_log-删除")
    @ApiOperation("sys_opt_log-删除")
    public HttpResult delete(@RequestBody @Validated OptLogDTO dto) {
        if (Objects.isNull(dto.getId()) && CollUtil.isEmpty(dto.getIdList())) {
            return HttpResult.error("未指定删除条件");
        }
        optLogCrud.remove(Wrappers.<OptLogDO>lambdaQuery()
                //加上数据权限条件 比如用户编号
                .eq(Objects.nonNull(dto.getId()), OptLogDO::getId, dto.getId())
                .in(CollUtil.isNotEmpty(dto.getIdList()), OptLogDO::getId, dto.getIdList()));
        return HttpResult.success();
    }
}

