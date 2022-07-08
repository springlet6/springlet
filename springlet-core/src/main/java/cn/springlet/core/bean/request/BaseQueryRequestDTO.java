package cn.springlet.core.bean.request;

import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.ApiModelProperty;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 查询 请求 参数基类
 *
 * @author watermelon
 * @time 2020/11/23
 */
public class BaseQueryRequestDTO extends BasePageQueryRequestDTO {
    private static final long serialVersionUID = 6145643040992571701L;

    @ApiModelProperty(value = "统一搜索条件", name = "keys")
    private String keys;

    @ApiModelProperty(value = "开始时间", name = "startTime")
    private Date startTime;

    @ApiModelProperty(value = "结束时间", name = "endTime")
    private Date endTime;


    //统一搜索条件，split列表
    @ApiModelProperty(hidden = true)
    private List<String> keysList;

    public String getKeys() {
        return keys;
    }

    public void setKeys(String keys) {
        this.keys = keys;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public List<String> getKeysList() {
        return keysList;
    }

    /**
     * keys 去头尾空格
     *
     * @return
     */
    public String keysTrim() {
        this.keys = StrUtil.trim(keys);
        return this.keys;
    }


    /**
     * 将 keys 分割成一个列表
     *
     *
     * @return
     */
    public List<String> splitKeys() {
        return this.splitKeys(",");
    }


    /**
     * 将 keys 分割成一个列表
     *
     * @return
     */
    public List<String> splitKeys(String split) {
        String keysTrim = keysTrim();
        if (StrUtil.isNotBlank(keysTrim)) {
            this.keysList = Arrays.asList(keysTrim.split(split));
        }
        return this.keysList;
    }

}
