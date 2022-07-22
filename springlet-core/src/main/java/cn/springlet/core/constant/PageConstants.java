package cn.springlet.core.constant;

/**
 * page相关常量
 *
 * @author watermelon
 * @time 2020/10/27
 */
public interface PageConstants {
    /**
     * 当前页参数
     */
    String PAGE_NUM_TAG = "pageNum";
    /**
     * 每页数量参数
     */
    String PAGE_SIZE_TAG = "pageSize";


    /**
     * 默认当前页
     */
    Long DEFAULT_PAGE_NUM = 1L;
    /**
     * 默认 每页数量
     */
    Long DEFAULT_PAGE_SIZE = 10L;

    /**
     * 默认当前页 str
     */
    String DEFAULT_STR_PAGE_NUM = "1";
    /**
     * 默认 每页数量 str
     */
    String DEFAULT_STR_PAGE_SIZE = "10";

}
