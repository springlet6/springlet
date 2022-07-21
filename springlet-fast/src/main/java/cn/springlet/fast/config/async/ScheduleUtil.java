package cn.springlet.fast.config.async;

import cn.hutool.extra.spring.SpringUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * ScheduledThreadPoolExecutor util
 * 用于一些可丢失的 短时间 异步任务
 *
 * @author watermelon
 * @since 2022/5/19
 */
@Component
public class ScheduleUtil {

    public static final String SCHEDULED_THREAD_POOL_EXECUTOR = "scheduledThreadPoolExecutor";

    //异步线程池
    @ConditionalOnMissingBean(name = SCHEDULED_THREAD_POOL_EXECUTOR)
    @Bean(SCHEDULED_THREAD_POOL_EXECUTOR)
    public ScheduledThreadPoolExecutor scheduledThreadPoolExecutor() {
        int size = Runtime.getRuntime().availableProcessors();
        return new ScheduledThreadPoolExecutor(size);
    }

    private static volatile ScheduledThreadPoolExecutor scheduledThreadPoolExecutor;

    private static void init() {
        if (scheduledThreadPoolExecutor == null) {
            synchronized (ScheduleUtil.class) {
                if (scheduledThreadPoolExecutor == null) {
                    scheduledThreadPoolExecutor = SpringUtil.getBean(SCHEDULED_THREAD_POOL_EXECUTOR, ScheduledThreadPoolExecutor.class);
                }
            }
        }
    }

    /**
     * ScheduleUtil.schedule(1000L,()->{
     * System.out.println("内容");
     * })
     *
     * @param delayTime
     * @param runnable
     * @return
     */
    public static ScheduledFuture<?> schedule(long delayTime, Runnable runnable) {
        init();
        return scheduledThreadPoolExecutor.schedule(runnable, delayTime, TimeUnit.MILLISECONDS);
    }
}
