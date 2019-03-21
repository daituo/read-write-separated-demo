package com.read.write.separated.demo.readwriteseparated;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 数据库实现了读写分离，读库和写库，一般select访问读库，非select访问写库，读库会定期同步写库的数据，达到数据一致
 *
 * 在应用代码层面解决数据库读写分离带来的影响：
 *  通过spring动态切换数据源 + Mybatis plugin实现，如果判断是select语句，则spring动态切换到读库，
 *  如果是非select语句，则spring动态切换到写库
 *
 * 疑问：在读写分离之后会不会造成事务主从切换错误？？？
 *  不会，在AOP设置动态织出时，都会清空DynamicDataSourceHolder的ThreadLocal
 *
 */
@SpringBootApplication
@MapperScan(basePackages = "com.read.write.separated.demo.readwriteseparated.mapper")
@EnableConfigurationProperties
@EnableTransactionManagement
public class ReadWriteSeparatedApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReadWriteSeparatedApplication.class, args);
    }

}
