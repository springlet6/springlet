package cn.springlet.redis.util;

import cn.hutool.extra.spring.SpringUtil;
import cn.springlet.redis.redisson.RedissonService;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Supplier;

/**
 * 锁
 *
 * @author watermelon
 * @time 2021/2/22
 */
@Slf4j
public class RedissonStaticUtil {

    private RedissonStaticUtil() {
    }

    private static volatile RedissonService redissonService;

    private static void init() {
        if (redissonService == null) {
            synchronized (RedissonStaticUtil.class) {
                if (redissonService == null) {
                    redissonService = SpringUtil.getBean(RedissonService.class);
                }
            }
        }
    }

    /**
     * 锁模板(手动释放的lock)
     *
     * @param name
     * @param supplier
     * @return
     */
    public static <T> T lockTemplate(String name, Supplier<T> supplier) {
        init();
        return redissonService.lockTemplate(name, supplier);
    }

    /**
     * 锁模板(手动释放的lock)
     *
     * @param name
     * @param runnable
     * @return
     */
    public static void lockTemplate(String name, Runnable runnable) {
        init();
        redissonService.lockTemplate(name, runnable);
    }


    /**
     * 锁模板(过期自动释放的lock)
     * 默认 2秒
     *
     * @param name
     * @param supplier
     * @return
     */
    public static <T> T lockExpireTemplate(String name, Supplier<T> supplier) {
        init();
        return redissonService.lockExpireTemplate(name, supplier);
    }

    /**
     * 锁模板(过期自动释放的lock)
     * 默认 2秒
     *
     * @param name
     * @param runnable
     * @return
     */
    public static void lockExpireTemplate(String name, Runnable runnable) {
        init();
        redissonService.lockExpireTemplate(name, runnable);
    }

    /**
     * 锁模板(过期自动释放的lock)
     *
     * @param name
     * @param expire   毫秒
     * @param supplier
     */
    public static <T> T lockExpireTemplate(String name, long expire, Supplier<T> supplier) {
        init();
        return redissonService.lockExpireTemplate(name, expire, supplier);
    }

    /**
     * 锁模板(过期自动释放的lock)
     *
     * @param name
     * @param expire   毫秒
     * @param runnable
     */
    public static void lockExpireTemplate(String name, long expire, Runnable runnable) {
        init();
        redissonService.lockExpireTemplate(name, expire, runnable);
    }


    /**
     * 锁模板(指定等待时间，且过期自动释放的lock)
     *
     * @param name
     * @param wait
     * @param expire   毫秒
     * @param supplier
     */
    public static <T> T tryLockExpireTemplate(String name, long wait, long expire, Supplier<T> supplier, Supplier<T> waitTimeoutSupplier) throws InterruptedException {
        init();
        return redissonService.tryLockExpireTemplate(name, wait, expire, supplier,waitTimeoutSupplier);
    }

    /**
     * 锁模板(指定等待时间，且过期自动释放的lock)
     *
     * @param name
     * @param wait
     * @param expire   毫秒
     * @param runnable
     */
    public static void tryLockExpireTemplate(String name, long wait, long expire, Runnable runnable, Runnable waitTimeoutRunnable) throws InterruptedException {
        init();
        redissonService.tryLockExpireTemplate(name, wait, expire, runnable,waitTimeoutRunnable);

    }


}
