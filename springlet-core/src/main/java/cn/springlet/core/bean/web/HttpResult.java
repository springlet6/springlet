package cn.springlet.core.bean.web;

import cn.hutool.core.date.DateUtil;
import cn.springlet.core.bean.vo.BaseVO;
import cn.springlet.core.enums.BaseResultCodeEnum;
import cn.springlet.core.enums.ResultCodeEnum;
import cn.springlet.core.exception.web_return.HttpResultAssertException;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * 统一定义返回前端对象
 *
 * @author springlet
 */
@Slf4j
@ApiModel(description = "web响应对象")
public class HttpResult<T> extends BaseVO {

    private static final long serialVersionUID = 1L;

    /**
     * 状态码
     */
    @ApiModelProperty(value = "状态码")
    private Integer code;

    /**
     * 返回消息
     */
    @ApiModelProperty(value = "返回消息")
    private String msg;

    /**
     * 服务器时间
     */
    @ApiModelProperty(value = "服务器时间")
    private String serverTime;

    /**
     * 数据对象
     */
    @ApiModelProperty(value = "数据对象")
    private T data;


    /**
     * 扩展参数信息
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(value = "扩展参数信息")
    private HashMap<String, Object> ext;

    /**
     * 初始化一个新创建的 HttpResult 对象
     *
     * @param code 状态码
     * @param msg  返回内容
     * @param data 数据对象
     */
    private HttpResult(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.serverTime = DateUtil.now();
        if (Objects.nonNull(data)) {
            this.data = data;
        }
    }

    /**
     * 是否成功，如果使用了其它的code,额外自行判断code值
     *
     * @return
     */
    public boolean isSuccess() {
        return ResultCodeEnum.SUCCESS.code().equals(code);
    }

    /**
     * 将扩展的 字段 放在 map 中
     *
     * @param k
     * @param v
     * @return
     */
    public synchronized Object put(String k, Object v) {
        if (this.ext == null) {
            this.ext = new HashMap<>();
        }
        return this.ext.put(k, v);
    }

    public String getServerTime() {
        return serverTime;
    }

    public void setServerTime(String serverTime) {
        this.serverTime = serverTime;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    public HashMap<String, Object> getExt() {
        return ext;
    }

    public void setExt(HashMap<String, Object> ext) {
        this.ext = ext;
    }


    /**
     * 返回自定义消息
     *
     * @param resultCodeEnum
     * @return
     */
    public static <T> HttpResult<T> div(BaseResultCodeEnum resultCodeEnum) {
        return HttpResult.div(resultCodeEnum, null);
    }

    /**
     * 返回自定义消息
     *
     * @param resultCodeEnum
     * @param data           数据对象
     * @return
     */
    public static <T> HttpResult<T> div(BaseResultCodeEnum resultCodeEnum, T data) {
        return HttpResult.div(resultCodeEnum.code(), resultCodeEnum.msg(), data);
    }


    /**
     * 返回成功消息
     *
     * @param code
     * @param msg  返回内容
     * @param data 数据对象
     * @return 成功消息
     */
    public static <T> HttpResult<T> div(int code, String msg, T data) {
        return new HttpResult<T>(code, msg, data);
    }

    /**
     * 返回成功消息
     *
     * @return 成功消息
     */
    public static <T> HttpResult<T> success() {
        return HttpResult.div(ResultCodeEnum.SUCCESS);
    }

    /**
     * 返回成功数据
     *
     * @return 成功消息
     */
    public static <T> HttpResult<T> success(T data) {
        return HttpResult.div(ResultCodeEnum.SUCCESS, data);
    }

    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @return
     */
    public static <T> HttpResult<T> success(String msg) {
        return HttpResult.div(ResultCodeEnum.SUCCESS.code(), msg, null);

    }


    /**
     * 返回成功消息
     *
     * @param code
     * @param data 数据对象
     * @return
     */
    public static <T> HttpResult<T> success(int code, T data) {
        return HttpResult.div(code, ResultCodeEnum.SUCCESS.msg(), data);
    }

    /**
     * 返回错误消息
     *
     * @return
     */
    public static <T> HttpResult<T> error() {
        return HttpResult.div(ResultCodeEnum.ERROR_REQUEST);
    }

    /**
     * 返回错误消息
     *
     * @param msg 返回内容
     * @return
     */
    public static <T> HttpResult<T> error(String msg) {
        return HttpResult.div(ResultCodeEnum.ERROR_REQUEST.code(), msg, null);
    }

    /**
     * 返回错误消息
     *
     * @param code 状态码
     * @param msg  返回内容
     * @return
     */
    public static <T> HttpResult<T> error(int code, String msg) {
        return HttpResult.div(code, msg, null);
    }

    /**
     * 断言 成功
     */
    public T assertSuccessAndGet() {
        if (!isSuccess()) {
            log.error("调用返回错误,{}", JSON.toJSONString(this));
            throw new HttpResultAssertException(msg);
        }
        return data;
    }

    /**
     * 断言 成功
     *
     * @param failMsg
     */
    public T assertSuccessAndGet(String failMsg) {
        if (!isSuccess()) {
            log.error("调用返回错误,{}", JSON.toJSONString(this));
            throw new HttpResultAssertException(failMsg);
        }
        return data;
    }


    /**
     * 断言 成功
     *
     * @param supplier 自定义 成功 / 失败
     */
    public T assertSuccessAndGet(Supplier<Boolean> supplier) {
        if (!supplier.get()) {
            log.error("调用返回错误,{}", JSON.toJSONString(this));
            throw new HttpResultAssertException(msg);
        }
        return data;
    }

    /**
     * 断言 成功
     *
     * @param supplier 自定义 成功 / 失败
     * @param failMsg
     */
    public T assertSuccessAndGet(Supplier<Boolean> supplier, String failMsg) {
        if (!supplier.get()) {
            log.error("调用返回错误,{}", JSON.toJSONString(this));
            throw new HttpResultAssertException(failMsg);
        }
        return data;
    }


    /**
     * 断言 成功
     */
    public void assertSuccess() {
        if (!isSuccess()) {
            log.error("调用返回错误,{}", JSON.toJSONString(this));
            throw new HttpResultAssertException(msg);
        }
    }

    /**
     * 断言 成功
     *
     * @param failMsg
     */
    public void assertSuccess(String failMsg) {
        if (!isSuccess()) {
            log.error("调用返回错误,{}", JSON.toJSONString(this));
            throw new HttpResultAssertException(failMsg);
        }
    }

    /**
     * 断言 成功
     *
     * @param supplier 自定义 成功 / 失败
     */
    public void assertSuccess(Supplier<Boolean> supplier) {
        if (!supplier.get()) {
            log.error("调用返回错误,{}", JSON.toJSONString(this));
            throw new HttpResultAssertException(msg);
        }
    }

    /**
     * 断言 成功
     *
     * @param supplier 自定义 成功 / 失败
     * @param failMsg
     */
    public void assertSuccess(Supplier<Boolean> supplier, String failMsg) {
        if (!supplier.get()) {
            log.error("调用返回错误,{}", JSON.toJSONString(this));
            throw new HttpResultAssertException(failMsg);
        }
    }
}
