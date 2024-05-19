package org.dubbo.distribute.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.dubbo.distribute.constant.CaffeineCacheConstants;
import org.dubbo.distribute.domain.pojo.AuthRole;
import org.dubbo.distribute.domain.pojo.AuthUser;
import org.dubbo.distribute.mapper.AuthRoleMapper;
import org.dubbo.distribute.mapper.AuthUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @description: 异步的去更新缓存中的数据作为缓存操作
 * @author: muqingfeng
 * @date: 2024/5/19 11:35
 */
@Slf4j
@Service
public class CaffeineAsyncService {
    private final AuthUserMapper authUserMapper;

    private final AuthRoleMapper authRoleMapper;

    private final AsyncLoadingCache<Object, Object> cache;


    @Autowired
    public CaffeineAsyncService(Caffeine<Object, Object> caffeineCacheBuilder, AuthUserMapper authUserMapper, AuthRoleMapper authRoleMapper) {
        this.authUserMapper = authUserMapper;
        this.authRoleMapper = authRoleMapper;
        // 确保在构造函数中只构建一次LoadingCache
        this.cache = caffeineCacheBuilder.buildAsync(new CacheLoader<Object, Object>() {
            //默认： 当缓存中根据 key 不能加载到指定值时， 则返回null
            //改进： 去查对应到数据库，然后重建缓存
            @Override
            public @Nullable Object load(@NonNull Object key) throws Exception {
                log.info("CaffeineAsyncService.load() : " + key);
                long l = System.currentTimeMillis();
                log.info("现在的时间是：" + System.currentTimeMillis());
                //匹配指定的前缀，走指定的查询
                if (key instanceof String) {
                    String keyWithoutPrefix = ((String) key).replace(CaffeineCacheConstants.cacheKeyPrefix, "");
                    if (keyWithoutPrefix.contains(CaffeineCacheConstants.prefixOfUser)) {
                        log.info("开始根据数据库查询用户信息");
                        String userName = keyWithoutPrefix.replace(CaffeineCacheConstants.prefixOfUser, "");
                        log.info("username: " + userName);
                        Optional<AuthUser> user = authUserMapper.findByUserName(userName);
                        log.info("user: " + user);
                        log.info("重新加载缓存成功， 耗时：" + (System.currentTimeMillis() - l) + "毫秒");
                        return user.orElse(null);
                    } else if (keyWithoutPrefix.contains(CaffeineCacheConstants.prefixOfRole)) {
                        log.info("开始根据数据库查询用户权限信息");
                        return authRoleMapper.selectOne(new QueryWrapper<AuthRole>(
                        ).eq("name", keyWithoutPrefix.replace(CaffeineCacheConstants.prefixOfRole, "")));
                    }
                }
                return null;
            }
        });
    }

    public void setCache(Object k, Object v) {
        log.info("异步的设置key value");
        log.info("异步开始时间：" + System.currentTimeMillis());
        cache.put(k, CompletableFuture.supplyAsync(() -> v));
        log.info("异步结束时间：" + System.currentTimeMillis());

    }

    public Object getCache(Object k) {
        CompletableFuture<Object> completableFuture = null;
        try {
            Objects.requireNonNull(k, "Cache key cannot be null");
            completableFuture = cache.get(k);
            return completableFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

    }
}
