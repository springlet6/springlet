package cn.springlet.mybatisplus.page;

import cn.springlet.core.bean.page.PageInfo;
import cn.springlet.core.bean.web.HttpResult;
import cn.springlet.core.enums.ResultCodeEnum;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * page相关返回对象
 * 整合  page 相关的 响应对象
 * 1.接受 实现了 mybatis-plus 的 com.baomidou.mybatisplus.core.metadata.IPage 接口的对象
 * * ====================================
 * *这里的 Page 是 实现了 com.baomidou.mybatisplus.core.metadata.IPage 接口的对象
 * * HttpResult httpResult = PageResult.httpSuccess(new Page<User>());
 * * RpcResult<PageInfo<PageResult>> pageInfoRpcResult = PageResult.rpcSuccess(new Page<User>());
 * * ====================================
 * <p>
 * <p>
 * 3.接受  com.lhs.cutepet.common.core.bean.page.PageInfo 对象
 * * ====================================
 * * HttpResult httpResult = PageResult.httpSuccess(PageInfo<User>());
 * * RpcResult<PageInfo<User>> pageInfoRpcResult = PageResult.rpcSuccess(PageInfo<User>());
 * * ====================================
 *
 * @author watermelon
 * @time 2020/10/26
 */
public class PageResult {

    private PageResult() {
    }

    /******************======================== mybatis-plus start ========================********************/

    /**
     * 传入一个 mybatis-plus 的分页结果对象
     * 返回 http 请求的 响应分页实体
     * success
     *
     * @param page
     * @return
     */
    public static <T> HttpResult<PageInfo<T>> httpSuccess(IPage<T> page) {
        return HttpResult.success(iPageToPageInfo(page));
    }

    /**
     * 传入一个 mybatis-plus 的分页结果对象
     * 返回 http 请求的 响应分页实体
     * success
     *
     * @param page
     * @param dataList
     * @return
     */
    public static <T> HttpResult<PageInfo<T>> httpSuccess(IPage page, List<T> dataList) {
        return HttpResult.success(iPageToPageInfo(page, dataList));
    }


    /**
     * 传入一个 mybatis-plus 的分页结果对象
     * 返回 http 请求的 响应分页实体
     * success
     *
     * @param msg
     * @param page
     * @return
     */
    public static <T> HttpResult<PageInfo<T>> httpSuccess(String msg, IPage<T> page) {
        return HttpResult.div(ResultCodeEnum.SUCCESS.code(), msg, iPageToPageInfo(page));
    }

    /**
     * 传入一个 mybatis-plus 的分页结果对象
     * 返回 http 请求的 响应分页实体
     * success
     *
     * @param msg
     * @param page
     * @param dataList
     * @return
     */
    public static <T> HttpResult<PageInfo<T>> httpSuccess(String msg, IPage page, List<T> dataList) {
        return HttpResult.div(ResultCodeEnum.SUCCESS.code(), msg, iPageToPageInfo(page, dataList));
    }

    /**
     * 传入一个 mybatis-plus 的分页结果对象
     * 返回 http 请求的 响应分页实体
     * error
     *
     * @param msg
     * @param page
     * @return
     */
    public static <T> HttpResult<PageInfo<T>> httpError(String msg, IPage<T> page) {
        return HttpResult.div(ResultCodeEnum.ERROR.code(), msg, iPageToPageInfo(page));
    }

    /**
     * 传入一个 mybatis-plus 的分页结果对象
     * 返回 http 请求的 响应分页实体
     * success
     *
     * @param msg
     * @param page
     * @param dataList
     * @return
     */
    public static <T> HttpResult<PageInfo<T>> httpError(String msg, IPage page, List<T> dataList) {
        return HttpResult.div(ResultCodeEnum.ERROR.code(), msg, iPageToPageInfo(page, dataList));
    }

    /******************======================== mybatis-plus end ========================********************/


    /******************========================  PageInfo start ========================********************/

    /**
     * 返回 http 请求的 响应分页实体
     * success
     *
     * @param page
     * @return
     */
    public static <T> HttpResult<PageInfo<T>> httpSuccess(PageInfo<T> page) {
        return HttpResult.success(page);
    }

    /**
     * 传入一个 com.lhs.cutepet.common.core.bean.page.PageInfo
     * 返回 http 请求的 响应分页实体
     * success
     *
     * @param msg
     * @param page
     * @return
     */
    public static <T> HttpResult<PageInfo<T>> httpSuccess(String msg, PageInfo<T> page) {
        return HttpResult.div(ResultCodeEnum.SUCCESS.code(), msg, page);
    }


    /**
     * 返回 http 请求的 响应分页实体
     * error
     *
     * @param page
     * @return
     */
    public static <T> HttpResult<PageInfo<T>> httpError(String msg, PageInfo<T> page) {
        return HttpResult.div(ResultCodeEnum.ERROR.code(), msg, page);
    }

    /******************======================== com.lhs.cutepet.common.core.bean.page.PageInfo end ========================********************/


    /**
     * 将 com.baomidou.mybatisplus.core.metadata.IPage 对象 转成 PageInfo 对象
     *
     * @param page
     * @param <T>
     * @return
     */
    public static <T> PageInfo<T> iPageToPageInfo(IPage<T> page) {
        PageInfo<T> pageInfo = new PageInfo<T>();
        if (page == null) {
            return pageInfo;
        }
        pageInfo.setPageNum(page.getCurrent());
        pageInfo.setPageSize(page.getSize());
        pageInfo.setTotal(page.getTotal());
        pageInfo.setRecords(page.getRecords());
        return pageInfo;
    }

    /**
     * 将 com.baomidou.mybatisplus.core.metadata.IPage 对象 转成 PageInfo 对象
     *
     * @param page
     * @param dataList
     * @param <T>
     * @return
     */
    public static <T> PageInfo<T> iPageToPageInfo(IPage page, List<T> dataList) {
        PageInfo<T> pageInfo = new PageInfo<T>();
        if (page == null) {
            return pageInfo;
        }
        pageInfo.setPageNum(page.getCurrent());
        pageInfo.setPageSize(page.getSize());
        pageInfo.setTotal(page.getTotal());
        pageInfo.setRecords(dataList);
        return pageInfo;
    }
}
