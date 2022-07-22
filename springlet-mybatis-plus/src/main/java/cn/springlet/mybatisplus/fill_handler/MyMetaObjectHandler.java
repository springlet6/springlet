package cn.springlet.mybatisplus.fill_handler;

import cn.hutool.core.date.DateTime;
import cn.springlet.core.constant.SqlConstants;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 填充器
 *
 * @author nieqiurong 2018-08-10 22:59:23.
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, SqlConstants.CREATE_TIME_PROPERTY_NAME, Date.class, DateTime.now());
        this.strictUpdateFill(metaObject, SqlConstants.UPDATE_TIME_PROPERTY_NAME, Date.class, DateTime.now());
        //乐观锁 初始化填充
        this.strictInsertFill(metaObject, SqlConstants.VERSION_PROPERTY_NAME, Long.class, 0L);
        //逻辑删除 初始化填充
        this.strictInsertFill(metaObject, SqlConstants.IS_DELETED_PROPERTY_NAME, Long.class, 0L);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, SqlConstants.UPDATE_TIME_PROPERTY_NAME, Date.class, DateTime.now());
    }
}
