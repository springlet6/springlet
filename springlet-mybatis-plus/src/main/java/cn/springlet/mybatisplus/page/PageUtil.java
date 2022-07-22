package cn.springlet.mybatisplus.page;

import cn.springlet.core.bean.request.BasePageQueryRequestDTO;
import cn.springlet.core.constant.PageConstants;
import cn.springlet.core.exception.web_return.ReturnMsgException;
import cn.springlet.core.util.ServletUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import javax.servlet.http.HttpServletRequest;

/**
 * 分页统一工具（整合 mybatis-plus 和 pageHelper 的分页器）
 * 开启分页 统一使用 util
 * 默认 分页参参数 1 10
 * mp   mybatis-plus
 * <p>
 * <p>
 * 使用 mybatis-plus 需要 将 返回 Page 对象作为参数 传入 mapper中的方法
 * 用法：
 * ====================================
 * *Page page = PageUtil.getMpPage();
 * *IPage page = mapper.selectPage(page,params);
 * ====================================
 * <p>
 *
 * @author wm
 * @email zfquan91@foxmail.com
 * @date 19-10-16
 */
public class PageUtil {


    private PageUtil() {
    }


    /**
     * 获取一个 mp 的基础分页对象
     *
     * @return
     */
    public static <T> Page<T> getMpPage() {
        return PageUtil.getMpPage(null, null);
    }

    /**
     * 获取一个 mp 的基础分页对象
     *
     * @param pageNum 当前页
     * @return
     */
    public static <T> Page<T> getMpPage(Long pageNum) {
        return PageUtil.getMpPage(pageNum, null);
    }

    /**
     * 获取一个 mp 的基础分页对象
     *
     * @param pageNum  当前页
     * @param pageSize 每页数量
     * @return
     */
    public static <T> Page<T> getMpPage(Long pageNum, Long pageSize) {
        if (pageNum == null) {
            pageNum = PageConstants.DEFAULT_PAGE_NUM;
        }
        if (pageSize == null) {
            pageSize = PageConstants.DEFAULT_PAGE_SIZE;
        }
        if (pageNum < 0) {
            pageNum = PageConstants.DEFAULT_PAGE_NUM;
        }
        if (pageSize < 0) {
            pageSize = PageConstants.DEFAULT_PAGE_SIZE;
        }
        Page<T> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        return page;
    }

    /**
     * 获取一个 mp 的基础分页对象
     *
     * @param queryDTO
     * @return
     */
    public static <T, Q extends BasePageQueryRequestDTO> Page<T> getMpPage(Q queryDTO) {
        return PageUtil.getMpPage(queryDTO.getPageNum(), queryDTO.getPageSize());
    }


    /**
     * 使用 request 传来的分页参数 获取一个 mp 的基础分页对象
     * 如果 不是 http 请求，则使用默认分页模型
     *
     * @return
     */
    public static <T> Page<T> getMpPageFromRequest() {
        HttpServletRequest request = ServletUtil.getRequest();
        if (request == null) {
            return getMpPage();
        }
        String pageNumStr = request.getParameter(PageConstants.PAGE_NUM_TAG);
        String pageSizeStr = request.getParameter(PageConstants.PAGE_SIZE_TAG);

        try {
            long pageNum = Long.parseLong(pageNumStr);
            long pageSize = Long.parseLong(pageSizeStr);
            return PageUtil.<T>getMpPage(pageNum, pageSize);
        } catch (NumberFormatException e) {
            //如果 分页参数不为 number类型 或者 分页参数 过大 超过  则会返回错误
            throw new ReturnMsgException("分页参数类型错误!");
        }
    }
}
