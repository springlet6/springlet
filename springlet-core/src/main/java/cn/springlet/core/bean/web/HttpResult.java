package cn.springlet.core.bean.web;

import cn.hutool.core.date.DateUtil;
import cn.springlet.core.bean.vo.BaseVO;
import cn.springlet.core.constant.ErrCodeStatus;
import cn.springlet.core.exception.web_return.HttpResultAssertException;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * 统一定义返回前端对象
 *
 * @author Administrator
 */
@Slf4j
public class HttpResult<T> extends BaseVO {

    private static final long serialVersionUID = 1L;

    public static final String SUCCESS_MSG = "请求成功";
    public static final String ERROR_MSG = "请求失败";


    /**
     * 状态码
     */
    private Integer code;

    /**
     * 返回内容
     */
    private String msg;

    /**
     * 服务器时间
     */
    private String serverTime;

    /**
     * 数据对象
     */
    private T data;


    /**
     * 扩展参数信息
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private HashMap<String, Object> ext;

    /**
     * 初始化一个新创建的 HttpResult 对象，使其表示一个空消息。
     */
    public HttpResult() {
    }

    /**
     * 初始化一个新创建的 HttpResult 对象
     *
     * @param msg 返回内容
     */
    public HttpResult(String msg) {
        this(ErrCodeStatus.SUCCESS, msg);
    }

    /**
     * 初始化一个新创建的 HttpResult 对象
     *
     * @param code 状态码
     * @param msg  返回内容
     */
    public HttpResult(int code, String msg) {
        this(code, msg, null);
    }

    /**
     * 初始化一个新创建的 HttpResult 对象
     *
     * @param code 状态码
     * @param msg  返回内容
     * @param data 数据对象
     */
    public HttpResult(int code, String msg, T data) {
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
        return ErrCodeStatus.SUCCESS == code;
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
     * 返回成功消息
     *
     * @return 成功消息
     */
    public static <T> HttpResult<T> success() {
        return HttpResult.success(SUCCESS_MSG);
    }

    /**
     * 返回成功数据
     *
     * @return 成功消息
     */
    public static <T> HttpResult<T> success(T data) {
        return HttpResult.success(SUCCESS_MSG, data);
    }

    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @return 成功消息
     */
    public static <T> HttpResult<T> success(String msg) {
        return new HttpResult<T>(msg);
    }

    /**
     * 返回成功消息
     *
     * @param msg  返回内容
     * @param data 数据对象
     * @return 成功消息
     */
    public static <T> HttpResult<T> success(String msg, T data) {
        return new HttpResult<T>(ErrCodeStatus.SUCCESS, msg, data);
    }

    /**
     * 返回成功消息
     *
     * @param code
     * @param msg  返回内容
     * @param data 数据对象
     * @return 成功消息
     */
    public static <T> HttpResult<T> success(int code, String msg, T data) {
        return new HttpResult<T>(code, msg, data);
    }

    /**
     * 返回成功消息
     *
     * @param code
     * @param data 数据对象
     * @return 成功消息
     */
    public static <T> HttpResult<T> success(int code, T data) {
        return new HttpResult<T>(code, SUCCESS_MSG, data);
    }

    /**
     * 返回错误消息
     *
     * @return
     */
    public static <T> HttpResult<T> error() {
        return HttpResult.error(ERROR_MSG);
    }

    /**
     * 返回错误消息
     *
     * @param msg 返回内容
     * @return 警告消息
     */
    public static <T> HttpResult<T> error(String msg) {
        return HttpResult.error(msg, null);
    }

    /**
     * 返回错误消息
     *
     * @param msg  返回内容
     * @param data 数据对象
     * @return 警告消息
     */
    public static <T> HttpResult<T> error(String msg, T data) {
        return new HttpResult<T>(ErrCodeStatus.ERROR, msg, data);
    }

    /**
     * 返回错误消息
     *
     * @param code 状态码
     * @param msg  返回内容
     * @return 警告消息
     */
    public static <T> HttpResult<T> error(int code, String msg) {
        return new HttpResult<T>(code, msg, null);
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
