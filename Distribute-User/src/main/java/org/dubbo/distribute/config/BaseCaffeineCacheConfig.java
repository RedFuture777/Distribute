package org.dubbo.distribute.config;

import lombok.Data;

/**
 * @description: 缓存基本类
 * @author: muqingfeng
 * @date: 2024/4/17 22:33
 */
@Data
public abstract class BaseCaffeineCacheConfig {
    /**
     * 缓存名称
     */
    private String name = "caffeine";

    /**
     * 默认最大容量， 大于 0 生效
     */
    private int maxSize = 100;

    /**
     * 缓存过期时间（秒），大于 0 生效
     */
    private int expireDuration = -1;

    /**
     * 缓存刷新时间（秒），大于0生效，且表示这是一个LoadingCache,否则表示是一个普通cache
     */
    private int refreshDuration = -1;


    public abstract Object getValue(Object key);
}
