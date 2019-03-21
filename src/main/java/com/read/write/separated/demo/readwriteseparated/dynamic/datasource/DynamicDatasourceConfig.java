package com.read.write.separated.demo.readwriteseparated.dynamic.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 读数据源和写数据源配置
 * @Author daituo
 * @Date 2019-3-21
 **/
@Configuration
/** 配置文件加载路径的配置 */
@PropertySource(value = "classpath:application-dev.yml")
public class DynamicDatasourceConfig {

    @Autowired
    private ApplicationContext applicationContext;

    /** 写数据库配置 */
    @Bean
    /** 在spring 中使用注解，常使用@Autowired，默认是根据类型Type来自动注入的。但有些特殊情况，对同一个接口，可能会有几种不同的实现类，
     * 而默认只会采取其中一种的情况下, @Primary表示优先使用该注解的bean*/
    @Primary
    @ConfigurationProperties(prefix = "write.datasource")
    public DataSource writeDataSource() {
        return DataSourceBuilder.create().type(DruidDataSource.class).build();
    }


    /** 读数据库配置 */
    @Bean
    @ConfigurationProperties(prefix = "read.datasource")
    public DataSource readDataSource() {
        return DataSourceBuilder.create().type(DruidDataSource.class).build();
    }


    /** 数据源配置 */
    @Bean
    public DynamicDataSource dynamicDataSource() {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        Map<Object, Object> dataSourceMap = new HashMap<>();
        String name = DynamicDataSourceGlobal.READ.name();
        dataSourceMap.put(DynamicDataSourceGlobal.READ.name(), readDataSource());
        dataSourceMap.put(DynamicDataSourceGlobal.WRITE.name(), writeDataSource());
        ///** 设置默认的数据源 */
        //dynamicDataSource.setDefaultTargetDataSource(writeDataSource());
        /** 传入数据源map，AbstractRoutingDataSource将以key来分配数据源 */
        dynamicDataSource.setTargetDataSources(dataSourceMap);
        return dynamicDataSource;
    }


    /** 配置事务管理 */
    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager() {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(dynamicDataSource());
        return transactionManager;
    }


    /** 配置SqlSessionFactory */
    @Bean
    public SqlSessionFactory getSqlSessionFactory(@Qualifier("dynamicDataSource") DynamicDataSource dynamicDataSource)
            throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dynamicDataSource);
        /** 必须手动指定mapper映射文件路径 */
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:mapper/**/*.xml"));
        /** 必须手动设置自定义的mybatis插件拦截器 */
        sqlSessionFactoryBean.setPlugins(new Interceptor[]{new DynamicDataSourceMybatisPlugin()});
        return sqlSessionFactoryBean.getObject();
    }


    //@Bean
    //public SqlSessionTemplate getSqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
    //    SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactory);
    //    return template;
    //}

}
