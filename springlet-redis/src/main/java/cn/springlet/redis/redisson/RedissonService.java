package cn.springlet.redis.redisson;

import cn.springlet.core.exception.web_return.ReturnMsgException;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * 锁
 *
 * @author watermelon
 * @time 2021/2/22
 */
@Slf4j
@Component
public class RedissonService {


    @Autowired
    private RedissonClient redissonClient;

    /**
     * 锁模板(手动释放的lock)
     *
     * @param name
     * @param supplier
     * @return
     */
    public <T> T lockTemplate(String name, Supplier<T> supplier) {
        RLock lock = redissonClient.getLock(name);
        try {
            lock.lock();
            log.info("lockTemplate->锁 {} 获取", name);
            return supplier.get();
        } finally {
            unlock("lockTemplate", lock, name);

        }
    }

    /**
     * 锁模板(手动释放的lock)
     *
     * @param name
     * @param runnable
     * @return
     */
    public void lockTemplate(String name, Runnable runnable) {
        RLock lock = redissonClient.getLock(name);
        try {
            lock.lock();
            log.info("lockTemplate->锁 {} 获取", name);
            runnable.run();
        } finally {
            unlock("lockTemplate", lock, name);

        }
    }


    /**
     * 锁模板(过期自动释放的lock)
     * 默认 5秒
     *
     * @param name
     * @param supplier
     * @return
     */
    public <T> T lockExpireTemplate(String name, Supplier<T> supplier) {
        return lockExpireTemplate(name, 5000, supplier);
    }

    /**
     * 锁模板(过期自动释放的lock)
     * 默认 5秒
     *
     * @param name
     * @param runnable
     * @return
     */
    public void lockExpireTemplate(String name, Runnable runnable) {
        lockExpireTemplate(name, 5000, runnable);
    }

    /**
     * 锁模板(过期自动释放的lock)
     *
     * @param name
     * @param expire   毫毫秒
     * @param supplier
     */
    public <T> T lockExpireTemplate(String name, long expire, Supplier<T> supplier) {
        RLock lock = redissonClient.getLock(name);
        try {
            lock.lock(expire, TimeUnit.MILLISECONDS);
            log.info("lockExpireTemplate->锁 {} 获取", name);
            return supplier.get();
        } finally {
            unlock("lockExpireTemplate", lock, name);

        }
    }

    /**
     * 锁模板(过期自动释放的lock)
     *
     * @param name
     * @param expire   毫秒
     * @param runnable
     */
    public void lockExpireTemplate(String name, long expire, Runnable runnable) {
        RLock lock = redissonClient.getLock(name);
        try {
            lock.lock(expire, TimeUnit.MILLISECONDS);
            log.info("lockExpireTemplate->锁 {} 获取", name);
            runnable.run();
        } finally {
            unlock("lockExpireTemplate", lock, name);

        }
    }


    /**
     * 锁模板(指定等待时间，且过期自动释放的lock)
     *
     * @param name
     * @param wait
     * @param expire   毫秒
     * @param supplier
     */
    public <T> T tryLockExpireTemplate(String name, long wait, long expire, Supplier<T> supplier, Supplier<T> waitTimeoutSupplier) throws InterruptedException {
        RLock lock = redissonClient.getLock(name);
        try {
            final boolean isGet = lock.tryLock(wait, expire, TimeUnit.MILLISECONDS);
            if (!isGet) {
                log.info("tryLockExpireTemplate->锁 {} 获取超时,执行超时逻辑", name);
                return waitTimeoutSupplier.get();
            }
            log.info("tryLockExpireTemplate->锁 {} 获取", name);
            return supplier.get();
        } finally {
            unlock("tryLockExpireTemplate", lock, name);

        }
    }

    /**
     * 锁模板(指定等待时间，且过期自动释放的lock)
     *
     * @param name
     * @param wait
     * @param expire   毫秒
     * @param runnable
     */
    public void tryLockExpireTemplate(String name, long wait, long expire, Runnable runnable, Runnable waitTimeoutRunnable) throws InterruptedException {
        RLock lock = redissonClient.getLock(name);
        try {
            final boolean isGet = lock.tryLock(wait, expire, TimeUnit.MILLISECONDS);
            if (!isGet) {
                log.info("tryLockExpireTemplate->锁 {} 获取超时,执行超时逻辑", name);
                waitTimeoutRunnable.run();
                return;
            }
            log.info("tryLockExpireTemplate->锁 {} 获取", name);
            runnable.run();
        } finally {
            unlock("tryLockExpireTemplate", lock, name);
        }
    }

    private void unlock(String method, RLock lock, String name) {
        try {
            lock.unlock();
            log.info("{}->锁 {} 释放", method, name);
        } catch (IllegalMonitorStateException e) {
            log.info("{}->锁{}已经被释放,无需再次释放,业务处理超时，事务回滚", method, name);
            throw new ReturnMsgException("业务处理超时");
        }
    }

}
