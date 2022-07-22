package cn.springlet.mybatisplus.handler;

import cn.hutool.core.math.Money;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author watermelon
 * @time 2020/10/23
 */
@MappedTypes({Money.class})
@MappedJdbcTypes(value = {JdbcType.BIGINT, JdbcType.NUMERIC, JdbcType.DECIMAL})
public class MoneyTypeHandler extends BaseTypeHandler<Money> {


    /**
     * @see BaseTypeHandler#setNonNullParameter(PreparedStatement,
     * int, Object, JdbcType)
     */
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Money parameter, JdbcType jdbcType) throws SQLException {
        ps.setLong(i, parameter.getCent());
    }

    /**
     * @see BaseTypeHandler#getNullableResult(ResultSet,
     * String)
     */
    @Override
    public Money getNullableResult(ResultSet rs, String columnName) throws SQLException {
        long result = rs.getLong(columnName);

        Money money = new Money();
        money.setCent(result);

        return money;
    }

    /**
     * @see BaseTypeHandler#getNullableResult(ResultSet, int)
     */
    @Override
    public Money getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        long result = rs.getLong(columnIndex);

        Money money = new Money();
        money.setCent(result);

        return money;
    }

    /**
     * @see BaseTypeHandler#getNullableResult(CallableStatement,
     * int)
     */
    @Override
    public Money getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        long result = cs.getLong(columnIndex);

        Money money = new Money();
        money.setCent(result);

        return money;
    }
}
