package cn.springlet.fast.config.async;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 强制 @Async 注解必须指定以下某个 线程池
 *
 * @author wm
 * @email zfquan91@foxmail.com
 * @date 19-12-2
 */
@EnableAsync
@Configuration
public class DefaultAsyncConfig {

    public static final String ABORT_POLICY_ASYNC_TASK_EXECUTOR = "abortPolicyAsyncTaskExecutor";
    public static final String CALLER_RUNS_POLICY_ASYNC_TASK_EXECUTOR = "callerRunsPolicyAsyncTaskExecutor";
    public static final String DISCARD_POLICY_ASYNC_TASK_EXECUTOR = "discardPolicyAsyncTaskExecutor";

    /**
     * 当任务添加到线程池中被拒绝时，它将抛出 RejectedExecutionException 异常
     *
     * @return
     */
    @Bean(ABORT_POLICY_ASYNC_TASK_EXECUTOR)
    @ConditionalOnMissingBean(name = ABORT_POLICY_ASYNC_TASK_EXECUTOR)
    public Executor abortPolicyAsyncTaskExecutor() {
        //核心线程池数量，方法: 返回可用处理器的Java虚拟机的数量。
        int size = Runtime.getRuntime().availableProcessors();

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //核心线程数
        executor.setCorePoolSize(size);
        //最大线程数
        executor.setMaxPoolSize(size * 5);
        //队列最大长度
        //executor.setQueueCapacity(size * 2);
        executor.setQueueCapacity(1024);
        //线程池维护线程所允许的空闲时间
        executor.setKeepAliveSeconds(60);
        executor.setThreadNamePrefix(ABORT_POLICY_ASYNC_TASK_EXECUTOR + "-");
        // 等待所有任务都完成再继续销毁其他的Bean
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());

        executor.setTaskDecorator(new MdcTaskDecorator());
        executor.initialize();
        return executor;
    }

    /**
     * 线程池
     * 当任务添加到线程池中被拒绝时，会在线程池当前正在运行的Thread线程池中处理被拒绝的任务。
     *
     * @return
     */
    @Bean(name = CALLER_RUNS_POLICY_ASYNC_TASK_EXECUTOR)
    @ConditionalOnMissingBean(name = CALLER_RUNS_POLICY_ASYNC_TASK_EXECUTOR)
    public Executor callerRunsPolicyAsyncTaskExecutor() {
        //核心线程池数量，方法: 返回可用处理器的Java虚拟机的数量。
        int size = Runtime.getRuntime().availableProcessors();

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //核心线程数
        executor.setCorePoolSize(size);
        //最大线程数
        executor.setMaxPoolSize(size * 5);
        //队列最大长度
        //executor.setQueueCapacity(size * 2);
        executor.setQueueCapacity(1024);
        //线程池维护线程所允许的空闲时间
        executor.setKeepAliveSeconds(60);
        executor.setThreadNamePrefix(CALLER_RUNS_POLICY_ASYNC_TASK_EXECUTOR + "-");
        // 等待所有任务都完成再继续销毁其他的Bean
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        executor.setTaskDecorator(new MdcTaskDecorator());
        executor.initialize();
        return executor;
    }


    /**
     * 线程池
     * 当任务添加到线程池中被拒绝时，线程池将丢弃被拒绝的任务。
     *
     * @return
     */
    @Bean(DISCARD_POLICY_ASYNC_TASK_EXECUTOR)
    @ConditionalOnMissingBean(name = DISCARD_POLICY_ASYNC_TASK_EXECUTOR)
    public Executor discardPolicyAsyncTaskExecutor() {
        //核心线程池数量，方法: 返回可用处理器的Java虚拟机的数量。
        int size = Runtime.getRuntime().availableProcessors();

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //核心线程数
        executor.setCorePoolSize(size);
        //最大线程数
        executor.setMaxPoolSize(size * 5);
        //队列最大长度
        //executor.setQueueCapacity(size * 2);
        executor.setQueueCapacity(1024);
        //线程池维护线程所允许的空闲时间
        executor.setKeepAliveSeconds(60);
        executor.setThreadNamePrefix(DISCARD_POLICY_ASYNC_TASK_EXECUTOR + "-");
        // 等待所有任务都完成再继续销毁其他的Bean
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());

        executor.setTaskDecorator(new MdcTaskDecorator());
        executor.initialize();
        return executor;
    }
}
