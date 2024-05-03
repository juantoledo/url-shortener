package io.houmlab.urlshortener.infrastructure.cache.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import io.houmlab.urlshortener.infrastructure.cache.UrlCache;


public class RedisUrlCache implements UrlCache {

    private final ValueOperations<String, String> ops;

    @Autowired
    public RedisUrlCache(StringRedisTemplate template, ValueOperations<String, String> ops) {
        this.ops = template.opsForValue();
    }

    @Override
    public void put(String key, String value) {
        ops.set(key, value);
    }

    @Override
    public String get(String key) {
        return ops.get(key);
    }

    @Override
    public void remove(String key) {
        ops.getOperations().delete(key);
    }

    @Override
    public boolean containsKey(String key) {
        Boolean hasKey = ops.getOperations().hasKey(key);
        return hasKey != null && hasKey;
    }

}
