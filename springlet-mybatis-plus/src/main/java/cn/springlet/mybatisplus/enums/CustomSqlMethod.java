package cn.springlet.mybatisplus.enums;

/**
 * 自定义 sql 方法
 *
 * @author watermelon
 * @time 2020/10/23
 */
public enum CustomSqlMethod {

    /**
     * 根据id查询一条数据 并加上 悲观锁
     */
    SELECT_BY_ID_FOR_UPDATE("selectByIdForUpdate", "根据id查询一条数据 并加上 悲观锁", "SELECT %s FROM %s WHERE %s=#{%s} %s for update"),
    ;

    private final String method;
    private final String desc;
    private final String sql;

    CustomSqlMethod(String method, String desc, String sql) {
        this.method = method;
        this.desc = desc;
        this.sql = sql;
    }

    public String getMethod() {
        return method;
    }

    public String getDesc() {
        return desc;
    }

    public String getSql() {
        return sql;
    }
}
