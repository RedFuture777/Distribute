package org.dubbo.distribute.config;

import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.checker.units.qual.K;
import org.junit.jupiter.api.Order;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.concurrent.TimeUnit;

/**
 * @description: Caffeine 缓存配置
 * @author: muqingfeng
 * @date: 2024/5/18 22:36
 */
@Slf4j
@Configuration
public class CaffeineConfig {

    /**
     * 创建基于Caffeine 的缓存管理器 Cache Manager
     * 初始化一些一些key存入
     */
    @Bean
    @Primary
    public CacheManager caffeineCacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("caffeineCache");
        cacheManager.setCaffeine(Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .initialCapacity(50)
                .maximumSize(150));
        return cacheManager;
    }

    @Bean(name = "caffeineCacheBuilder")
    public Caffeine<Object, Object> caffeineCacheBuilder() {
        return Caffeine.newBuilder()
                .expireAfterWrite(5, TimeUnit.SECONDS)
                .refreshAfterWrite(3, TimeUnit.SECONDS)
                .initialCapacity(100)
                .maximumSize(200)
                .removalListener((key, value, cause) -> {
                    log.info("被移除的键key: " + key + " 被移除键的值value: " + value + " 被移除原因cause: " + cause);
                })
                .recordStats();
    }

    @Bean(name = "caffeineAsyncCacheBuilder")
    public Caffeine<Object, Object> caffeineAsyncCacheBuilder() {
        return Caffeine.newBuilder()
                .expireAfterWrite(5, TimeUnit.SECONDS)
                .refreshAfterWrite(3, TimeUnit.SECONDS)
                .initialCapacity(100)
                .maximumSize(200)
                .removalListener((key, value, cause) -> {
                    log.info("被移除的键key: " + key + " 被移除键的值value: " + value + " 被移除原因cause: " + cause);
                })
                .recordStats();
    }

}
