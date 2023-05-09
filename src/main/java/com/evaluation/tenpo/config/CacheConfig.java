package com.evaluation.tenpo.config;

import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;

@Configuration
@EnableCaching
public class CacheConfig {

    @Value("${ttl.redis.minute}")
    Integer ttlRedis;
    
    
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory factory){
       RedisCacheConfiguration redisCacheConfiguration= RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(ttlRedis));
       return RedisCacheManager.builder(factory).cacheDefaults(redisCacheConfiguration).build();
    }
}
