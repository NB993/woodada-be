package com.woodada.common;

import java.util.Set;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisInitialization {

    private final RedisTemplate<String, String> redisTemplate;

    public RedisInitialization(final RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void initRedis() {
        final Set<String> keys = redisTemplate.keys("*");

        for (String key : keys) {
            redisTemplate.delete(key);
        }
    }
}

