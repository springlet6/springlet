package cn.springlet.core.bean.page;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * 分页数据 实体
 *
 * @author watermelon
 * @time 2020/10/26
 */
public class PageInfo<T> implements Serializable {
    private static final long serialVersionUID = -1779295009877720036L;

    /**
     * 当前页
     */
    @ApiModelProperty(value = "页码", name = "pageNum")
    private Long pageNum;

    /**
     * 每页数量
     */
    @ApiModelProperty(value = "每页数量", name = "pageSize")
    private Long pageSize;

    /**
     * 总数
     */
    @ApiModelProperty(value = "总数", name = "total")
    private Long total = 0L;

    /**
     * 记录列表 服务间调用必须指定类型
     */
    @ApiModelProperty(value = "记录列表", name = "records")
    private List<T> records = Collections.emptyList();


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

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

}
