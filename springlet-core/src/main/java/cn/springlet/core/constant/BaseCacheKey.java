package cn.springlet.core.constant;

/**
 * cache key
 * * spring管理的cache key
 * <p> 如果 redis 中不存在缓存则走数据库，如果存在则走缓存
 * * @Cacheable(cacheNames = BaseCacheKey.REGION_KEY, key = "#regionId")
 * * public ComRegionDO getRegionById(Long regionId) {}
 * <p>
 * <p>清除缓存对应的缓存
 * * @CacheEvict(cacheNames = BaseCacheKey.REGION_KEY, key = "#regionId")
 * * public void cleanRegionCache(Long regionId) {}
 * <p>
 *
 *  使用 @Cacheable 生成的 redis 缓存, 随机key之前一共有3个冒号 ":::"
 * 使用 redisService 清除缓存要注意这一点
 *
 * @author watermelon
 * @time 2020/12/1
 */
public interface BaseCacheKey {
    /**
     * key 前缀
     */
    String PREFIX = "springlet:";


    /**
     * LIST 列表缓存的 LIST
     */
    String LIST = "'LIST'";
}
