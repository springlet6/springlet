package cn.springlet.core.bean.request;

import io.swagger.annotations.ApiModelProperty;

/**
 * 分页 查询 请求 参数基类
 *
 * @author watermelon
 * @time 2020/11/23
 */
public class BasePageQueryRequestDTO extends BaseRequestDTO {

    //导出 最大值
    public static final Long EXPORT_SIZE = 50000L;

    private static final long serialVersionUID = 6435581128839291782L;

    @ApiModelProperty(value = "当前页", name = "pageNum")
    private Long pageNum = 1L;

    @ApiModelProperty(value = "每页数量", name = "pageSize")
    private Long pageSize = 10L;


    public Long getPageNum() {
        return pageNum;
    }

    public void setPageNum(Long pageNum) {
        this.pageNum = pageNum;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }

    //设置导出参数
    public void exportTrue() {
        this.pageNum = 1L;
        this.pageSize = EXPORT_SIZE;
    }
}
