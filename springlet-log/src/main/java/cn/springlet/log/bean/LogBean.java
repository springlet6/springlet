package cn.springlet.log.bean;

import cn.hutool.core.util.StrUtil;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;


/**
 * 日志实体类
 *
 * @author watermelon
 * @time 2020/10/20
 */
@Setter
@Getter
public class LogBean implements Serializable {
    private static final long serialVersionUID = -8127325270756589149L;

    public LogBean() {
    }

    public LogBean(LogBean bean) {
        this.setTitle(bean.getTitle());
        this.setClassName(bean.getClassName());
        this.setMethodName(bean.getMethodName());
        this.setParameter(bean.getParameter());
        this.setReturnObject(bean.getReturnObject());
        this.setStartTime(bean.getStartTime());
        this.setEndTime(bean.getEndTime());
    }

    /**
     * 日志标题
     */
    private String title;
    /**
     * 类名
     */
    private String className;
    /**
     * 方法名
     */
    private String methodName;
    /**
     * 参数
     */
    private String parameter;
    /**
     * 返回结果
     */
    private String returnObject;
    /**
     * 开始时间
     */
    private LocalDateTime startTime;
    /**
     * 结束时间
     */
    private LocalDateTime endTime;

    /**
     * 是否是返回
     */
    private boolean returnTag = false;


    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.title);
        stringBuilder.append("->");
        assemblyLog(stringBuilder);
        return stringBuilder.toString();
    }


    /**
     * 其他日志
     * 需要输出其他信息可以重写该方法 并向  stringBuilder 加入要输出的日志信息
     *
     * @param stringBuilder
     */
    protected void otherLog(StringBuilder stringBuilder) {

    }

    /**
     * 关闭输出一些日志
     * 用于在输出返回值的时候，关闭输出一些信息
     * 可由子类重写该方法
     */
    public void closeSomeLog() {
        this.returnTag = true;
    }

    /**
     * 获取要输出的log
     *
     * @param stringBuilder
     */
    protected void assemblyLog(StringBuilder stringBuilder) {

        if (!this.returnTag && StrUtil.isNotBlank(this.parameter)) {
            stringBuilder.append("参数:");
            stringBuilder.append(this.parameter);
        }

        if (this.returnTag && StrUtil.isNotBlank(this.returnObject)) {
            stringBuilder.append("返回值:");
            stringBuilder.append(this.returnObject);

        }

        if (!this.returnTag) {
            stringBuilder.append(", ");
            stringBuilder.append("方法:");
            stringBuilder.append(this.className);
            stringBuilder.append("#");
            stringBuilder.append(this.methodName);
        }


        otherLog(stringBuilder);

        if (Objects.nonNull(this.startTime) && Objects.nonNull(this.endTime)) {
            stringBuilder.append(", ");
            stringBuilder.append("执行时间:");
            stringBuilder.append(Duration.between(startTime, endTime).toMillis());
            stringBuilder.append("毫秒");
        }


    }
}
