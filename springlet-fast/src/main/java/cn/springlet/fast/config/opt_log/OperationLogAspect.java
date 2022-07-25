
package cn.springlet.fast.config.opt_log;

import cn.springlet.core.util.BeanUtil;
import cn.springlet.fast.bean.entity.OptLogDO;
import cn.springlet.log.aspect.BaseOperationLogAspect;
import cn.springlet.log.bean.OptLogBean;
import cn.springlet.log.bean.OptUserBean;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;


/**
 * 系统日志，切面处理类
 *
 * @author watermelon
 * @time 2020/10/20
 */
@Slf4j
@Aspect
@Component
public class OperationLogAspect extends BaseOperationLogAspect {

    @Override
    protected OptUserBean getOptUserBean() {
        return new OptUserBean().setOptId("1").setOptAccount("123").setOptName("123");
    }

    @Override
    protected void insert(OptLogBean optLogBean) {
        BeanUtil.toBean(optLogBean, OptLogDO.class).insert();
    }
}
