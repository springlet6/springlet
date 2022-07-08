package cn.springlet.fast;

import cn.springlet.core.auto.async.ScheduleUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        bean.setId(1111L);
        log.info("{}", bean);
        return bean;
    }


}
