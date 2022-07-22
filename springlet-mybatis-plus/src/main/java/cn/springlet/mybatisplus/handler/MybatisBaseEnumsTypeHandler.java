package cn.springlet.mybatisplus.handler;

import cn.springlet.core.enums.DatabaseEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author watermelon
 * @time 2020/10/23
 */
@MappedTypes({DatabaseEnum.class})
public class MybatisBaseEnumsTypeHandler<E extends DatabaseEnum> extends BaseTypeHandler<E> {

    private Class<E> eClass;

    public MybatisBaseEnumsTypeHandler(Class<E> eClass) {
        if (eClass == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        this.eClass = eClass;

    }

    public MybatisBaseEnumsTypeHandler() {

    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
        ps.setObject(i, parameter.getDataBaseKey());
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        Object i = rs.getObject(columnName);
        if (rs.wasNull()) {
            return null;
        }
        try {
            return (E) DatabaseEnum.valueOf(eClass, i);
        } catch (Exception e) {
            throw new IllegalArgumentException("cannot convert " + i + " to " + e.getClass().getSimpleName(), e);
        }
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        Object i = rs.getObject(columnIndex);
        if (rs.wasNull()) {
            return null;
        }
        try {
            return (E) DatabaseEnum.valueOf(eClass, i);
        } catch (Exception e) {
            throw new IllegalArgumentException("cannot convert " + i + " to " + e.getClass().getSimpleName(), e);
        }
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        Object i = cs.getInt(columnIndex);
        if (cs.wasNull()) {
            return null;
        }
        try {
            return (E) DatabaseEnum.valueOf(eClass, i);
        } catch (Exception e) {
            throw new IllegalArgumentException("cannot convert " + i + " to " + e.getClass().getSimpleName(), e);
        }
    }
}
