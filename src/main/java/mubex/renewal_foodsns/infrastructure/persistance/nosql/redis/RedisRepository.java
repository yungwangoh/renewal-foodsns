package mubex.renewal_foodsns.infrastructure.persistance.nosql.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mubex.renewal_foodsns.infrastructure.persistance.nosql.KeyValueRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class RedisRepository implements KeyValueRepository {

    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public void set(final String key, final String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public String get(final String key) {
        return redisTemplate.opsForValue().get(key);
    }
}
