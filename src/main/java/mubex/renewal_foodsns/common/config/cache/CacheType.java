package mubex.renewal_foodsns.common.config.cache;

import lombok.Getter;

@Getter
public enum CacheType {

    POST_CACHE("post_cache", 3, 100),
    DIRTY_CHECK_CACHE("post_dirty_check", 1, 1000);

    private final String cacheName;     // 캐시 이름
    private final int expireAfterWrite; // 만료시간
    private final int maximumSize;      // 최대 갯수

    CacheType(final String cacheName, final int expireAfterWrite, final int maximumSize) {
        this.cacheName = cacheName;
        this.expireAfterWrite = expireAfterWrite;
        this.maximumSize = maximumSize;
    }
}
