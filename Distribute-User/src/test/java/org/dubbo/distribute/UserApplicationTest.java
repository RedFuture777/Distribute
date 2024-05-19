package org.dubbo.distribute;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.dubbo.distribute.constant.CaffeineCacheConstants;
import org.dubbo.distribute.service.impl.CaffeineAsyncService;
import org.dubbo.distribute.service.impl.CaffeineService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;
import java.util.concurrent.ExecutionException;

/**
 * @description: some desc
 * @author: muqingfeng
 * @date: 2024/4/15 22:13
 */

@Slf4j
@SpringBootTest
public class UserApplicationTest {

    @Autowired
    private CaffeineService caffeineService;

    @Autowired
    private CaffeineAsyncService caffeineAsyncService;

    @Autowired
    DataSource dataSource;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    public void contextLoads() throws SQLException {
        System.out.println(dataSource.getClass());
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
        DruidDataSource druidDataSource = (DruidDataSource) dataSource;
        System.out.println("druidDataSource 数据源的最大连接数：" + druidDataSource.getMaxActive());
        System.out.println("druidDataSource 数据源初始化连接数：" + druidDataSource.getInitialSize());
        connection.close();

    }


    @Test
    public void testRedis() {
//        Jedis jedis = new Jedis("121.40.166.14", 6379);
//        jedis.auth("redis123");
//        Set<String> keys = jedis.keys("*");
//        keys.forEach(System.out::println);

        Set<String> keys = redisTemplate.keys("*");
        keys.forEach(System.out::println);
        int[] nums = {1, 1, 3, 4, 5, 6};

    }

    @Test
    public void testCaffeine() {
        //type 1
        Cache<String, String> cache = Caffeine.newBuilder().build();

        cache.getIfPresent("1");    // null
        cache.get("1", k -> "1");    // 1
        cache.getIfPresent("1");    //1
        cache.put("1", "2");
        cache.getIfPresent("1");    //2

        //type 2
        Caffeine.newBuilder().build(new CacheLoader<Object, Object>() {
            @Override
            public @Nullable Object load(@NonNull Object o) throws Exception {
                return null;
            }
        });

    }


    @Test
    public void testCache() throws InterruptedException {
        String userKey = CaffeineCacheConstants.cacheKeyPrefix + CaffeineCacheConstants.prefixOfUser;
        String roleKey = CaffeineCacheConstants.cacheKeyPrefix + CaffeineCacheConstants.prefixOfRole;

        //先存入一个缓存
        log.info("先加入初始缓存 caffeine_user_张三 : nonono");
        caffeineService.setCache(userKey + "张三", "nonono");
        log.info("caffeineService.getCache(userKey) = " + caffeineService.getCache(userKey + "张三"));
        Thread.sleep(6000);
        //查询user
        log.info("caffeineService.getCache(userKey) = " + caffeineService.getCache(userKey + "张三"));

        Thread.sleep(10000);
        log.info("caffeineService.getCache(userKey) = " + caffeineService.getCache(userKey + "张三"));
        Thread.sleep(10000);
        log.info("caffeineService.getCache(userKey) = " + caffeineService.getCache(userKey + "张三"));
        Thread.sleep(10000);

    }

    @Test
    public void testAsyncCache() throws InterruptedException, ExecutionException {
        String userKey = CaffeineCacheConstants.cacheKeyPrefix + CaffeineCacheConstants.prefixOfUser;
        String roleKey = CaffeineCacheConstants.cacheKeyPrefix + CaffeineCacheConstants.prefixOfRole;

        //先存入一个缓存
        log.info("先加入初始缓存 caffeine_user_张三 : nonono");
        caffeineAsyncService.setCache(userKey + "张三", "nonono");
        log.info("caffeineAsyncService.getCache(userKey) = " + caffeineAsyncService.getCache(userKey + "张三"));
        log.info("caffeineAsyncService.getCache(userKey) = " + caffeineAsyncService.getCache(userKey + "张三"));
        log.info("caffeineAsyncService.getCache(userKey) = " + caffeineAsyncService.getCache(userKey + "张三"));
        log.info("caffeineAsyncService.getCache(userKey) = " + caffeineAsyncService.getCache(userKey + "张三"));


        while(true){

        }

    }


}
