package cn.springlet.log.bean;

import cn.springlet.core.util.ServletUtil;
import cn.springlet.log.aspect.BasePrintLogAspect;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;


/**
 * http请求
 * 日志实体类
 *
 * @author watermelon
 * @time 2020/10/20
 */
@Setter
@Getter
public class HttpLogBean extends LogBean {


    private static final long serialVersionUID = -5696019829680542363L;

    public HttpLogBean() {
        init();
    }


    public HttpLogBean(LogBean bean) {
        super(bean);
        init();
    }

    /**
     * 请求url
     */
    private String requestURI;
    /**
     * 请求方式
     */
    private String requestMethod;
    /**
     * 请求地址
     */
    private String remoteAddr;

    private boolean isHttpRequest = true;


    /**
     * 初始化
     * http 相关信息
     */
    private void init() {
        HttpServletRequest request = ServletUtil.getRequest();
        if (request != null) {
            this.setRemoteAddr(ServletUtil.getClientIP(request));
            this.setRequestMethod(request.getMethod());
            this.setRequestURI(request.getRequestURI());
        }
        if (StringUtils.isBlank(this.getTitle()) || BasePrintLogAspect.SERVICE_TITLE.equals(this.getTitle())) {
            this.setTitle(BasePrintLogAspect.HTTP_TITLE);
        }
    }

    @Override
    protected void otherLog(StringBuilder stringBuilder) {
        if (this.isHttpRequest) {
            stringBuilder.append(", ");
            stringBuilder.append("请求接口:");
            stringBuilder.append(this.requestURI);
            stringBuilder.append(", ");
            stringBuilder.append("请求方式:");
            stringBuilder.append(this.requestMethod);
            stringBuilder.append(", ");
            stringBuilder.append("来源IP:");
            stringBuilder.append(this.remoteAddr);
        }

    }

    @Override
    public void closeSomeLog() {
        super.closeSomeLog();
        this.isHttpRequest = false;
    }
}
