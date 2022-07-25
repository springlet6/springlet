package cn.springlet.redis.constant;

import cn.springlet.core.constant.BaseCacheKey;

/**
 * @author watermelon
 * @time 2020/12/1
 */
public interface RedisCacheKey extends BaseCacheKey {

    String NO_REPEAT_SUBMIT_KEY = PREFIX + "NO_REPEAT_SUBMIT_KEY:";
}
