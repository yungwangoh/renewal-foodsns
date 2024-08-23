package mubex.renewal_foodsns.common.config.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
    public CacheManager cacheManager(final List<CaffeineCache> caches) {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(caches);

        return cacheManager;
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
