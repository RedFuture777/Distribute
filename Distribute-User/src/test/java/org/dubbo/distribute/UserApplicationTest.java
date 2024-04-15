package org.dubbo.distribute;

import com.alibaba.druid.pool.DruidDataSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @description: some desc
 * @author: muqingfeng
 * @date: 2024/4/15 22:13
 */
@SpringBootTest
public class UserApplicationTest {

    @Autowired
    DataSource dataSource;


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

}
