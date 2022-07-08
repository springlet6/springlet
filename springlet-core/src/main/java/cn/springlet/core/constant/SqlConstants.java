package cn.springlet.core.constant;

/**
 * sql 常量
 *
 * @author watermelon
 * @time 2021/1/13
 */

public class SqlConstants {

    /**
     * for update
     */
    public static final String FOR_UPDATE = "for update";


    /**
     * LIMIT_ONE
     */
    public static final String LIMIT_ONE = "limit 1";

    /**
     * 创建时间字段
     */
    public static final String CREATE_TIME = "create_time";

    /**
     * 创建时间字段 类字段名
     */
    public static final String CREATE_TIME_PROPERTY_NAME = "createTime";

    /**
     * 修改时间字段
     */
    public static final String UPDATE_TIME = "update_time";

    /**
     * 修改时间字段 类字段名
     */
    public static final String UPDATE_TIME_PROPERTY_NAME = "updateTime";


    /**
     * 乐观锁字段
     */
    public static final String VERSION = "version";

    /**
     * 乐观锁字段 类字段名
     */
    public static final String VERSION_PROPERTY_NAME = "version";


    /**
     * 逻辑删除字段
     */
    public static final String IS_DELETED = "is_deleted";

    /**
     * 逻辑删除字段 类字段名
     */
    public static final String IS_DELETED_PROPERTY_NAME = "isDeleted";
}

