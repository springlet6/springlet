package cn.springlet.mybatisplus.injector.methods;

import cn.springlet.mybatisplus.annotation.DisabledModify;
import cn.springlet.mybatisplus.injector.AbstractCustomMethod;
import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * 根据 whereEntity 条件，更新记录
 * 替换掉 mp 原生的 Update
 * 增加 解析 注解 {@link DisabledModify}, 实体类中标注了 @DisabledModify 的字段, 将 不会生成 对应的 set 语句
 *
 * @author watermelon
 * @time 2020/12/2
 */
public class Update extends AbstractCustomMethod {

    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        SqlMethod sqlMethod = SqlMethod.UPDATE;
        String sql = String.format(sqlMethod.getSql(), tableInfo.getTableName(),
                sqlSet(true, true, tableInfo, true, ENTITY, ENTITY_DOT),
                sqlWhereEntityWrapper(true, tableInfo), sqlComment());
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
        return this.addUpdateMappedStatement(mapperClass, modelClass, getMethod(sqlMethod), sqlSource);
    }
}
