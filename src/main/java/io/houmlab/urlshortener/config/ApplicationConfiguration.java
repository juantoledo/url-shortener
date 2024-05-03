package io.houmlab.urlshortener.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import io.houmlab.urlshortener.infrastructure.cache.UrlCache;
import io.houmlab.urlshortener.infrastructure.cache.impl.RedisUrlCache;



@Configuration
public class ApplicationConfiguration {
    
    @Bean
    public UrlCache urlCache(StringRedisTemplate template) {
        ValueOperations<String, String> ops = template.opsForValue();
        return new RedisUrlCache(template, ops);
    }

}