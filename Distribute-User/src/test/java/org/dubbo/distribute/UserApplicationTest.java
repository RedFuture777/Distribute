package org.dubbo.distribute;

import com.alibaba.druid.pool.DruidDataSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.Jedis;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;

/**
 * @description: some desc
 * @author: muqingfeng
 * @date: 2024/4/15 22:13
 */
@SpringBootTest
public class UserApplicationTest {

    @Autowired
    DataSource dataSource;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    public void contextLoads() throws SQLException {
        System.out.println(dataSource.getClass());
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
        DruidDataSource druidDataSource  = (DruidDataSource) dataSource;
        System.out.println("druidDataSource 数据源的最大连接数："+ druidDataSource.getMaxActive());
        System.out.println("druidDataSource 数据源初始化连接数："+ druidDataSource.getInitialSize());
        connection.close();
    }


    @Test
    public void testRedis(){
//        Jedis jedis = new Jedis("121.40.166.14", 6379);
//        jedis.auth("redis123");
//        Set<String> keys = jedis.keys("*");
//        keys.forEach(System.out::println);

        Set<String> keys = redisTemplate.keys("*");
        keys.forEach(System.out::println);
    }



}
