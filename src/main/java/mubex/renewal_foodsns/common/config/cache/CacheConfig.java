package mubex.renewal_foodsns.common.config.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.CompositeCacheManager;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;

@EnableCaching
@Configuration
public class CacheConfig {

    @Bean
    public List<CaffeineCache> caches() {
        return Arrays.stream(CacheType.values())
                .map(cacheType -> new CaffeineCache(cacheType.getCacheName(),
                        Caffeine.newBuilder()
                                .expireAfterWrite(cacheType.getExpireAfterWrite(), TimeUnit.HOURS)
                                .maximumSize(cacheType.getMaximumSize())
                                .build()
                )).toList();
    }

    @Bean
    public CacheManager caffeineCacheManager(final List<CaffeineCache> caches) {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(caches);

        return cacheManager;
    }

    @Bean
    public CacheManager redisCacheManager(final RedisConnectionFactory redisConnectionFactory) {

        final RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(30))
                .enableTimeToIdle();

        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(redisCacheConfiguration)
                .build();
    }

    @Bean
    @Primary
    public CacheManager compositeCacheManager(
            @Qualifier("caffeineCacheManager") final CacheManager caffeineCacheManager,
            @Qualifier("redisCacheManager") final CacheManager redisCacheManager) {

        final CompositeCacheManager compositeCacheManager = new CompositeCacheManager(caffeineCacheManager,
                redisCacheManager);

        compositeCacheManager.setFallbackToNoOpCache(true);

        return caffeineCacheManager;
    }

    @Bean
    public Cache<Object, Object> cache(final CacheManager cacheManager) {
        org.springframework.cache.Cache springCache = cacheManager.getCache(CacheType.POST_CACHE.getCacheName());
        if (springCache instanceof CaffeineCache) {
            return ((CaffeineCache) springCache).getNativeCache();
        }

        return null;
    }
}
