package cn.springlet.fast.crud.impl;

import cn.springlet.fast.bean.entity.OptLogDO;
import cn.springlet.fast.crud.OptLogCrud;
import cn.springlet.fast.dao.OptLogDAO;
import cn.springlet.mybatisplus.service.impl.CustomBaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * sys_opt_log 单表相关操作实现类
 * </p>
 *
 * @author springlet
 * @since 2022-07-25
 */
@Service
public class OptLogCrudImpl extends CustomBaseServiceImpl<OptLogDAO, OptLogDO> implements OptLogCrud {

}
