package cn.springlet.core.auto.async;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import static cn.hutool.extra.spring.SpringUtil.getBean;

/**
 * 异步
 *
 * @author watermelon
 * @since 2022/5/19
 */
@Component
public class AsyncUtil {


    private static volatile AsyncUtil asyncUtil;

    private static void init() {
        if (asyncUtil == null) {
            synchronized (AsyncUtil.class) {
                if (asyncUtil == null) {
                    asyncUtil = getBean(AsyncUtil.class);
                }
            }
        }
    }


    /**
     * 当任务添加到线程池中被拒绝时，它将抛出 RejectedExecutionException 异常
     */
    public static void apAsync(Runnable runnable) {
        init();
        asyncUtil.abortPolicyAsyncOpt(runnable);
    }

    /**
     * 当任务添加到线程池中被拒绝时，会在线程池当前正在运行的Thread线程池中处理被拒绝的任务。
     */
    public static void crpAsync(Runnable runnable) {
        init();
        asyncUtil.callerRunsPolicyAsyncOpt(runnable);
    }


    /**
     * 当任务添加到线程池中被拒绝时，线程池将丢弃被拒绝的任务。
     */
    public static void dpAsync(Runnable runnable) {
        init();
        asyncUtil.discardPolicyAsyncOpt(runnable);
    }

    @Async(DefaultAsyncConfig.ABORT_POLICY_ASYNC_TASK_EXECUTOR)
    public void abortPolicyAsyncOpt(Runnable runnable) {

        runnable.run();
    }


    @Async(DefaultAsyncConfig.CALLER_RUNS_POLICY_ASYNC_TASK_EXECUTOR)
    public void callerRunsPolicyAsyncOpt(Runnable runnable) {

        runnable.run();
    }


    @Async(DefaultAsyncConfig.DISCARD_POLICY_ASYNC_TASK_EXECUTOR)
    public void discardPolicyAsyncOpt(Runnable runnable) {

        runnable.run();
    }
}
