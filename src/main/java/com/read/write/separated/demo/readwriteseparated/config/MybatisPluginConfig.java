//package com.read.write.separated.demo.readwriteseparated.config;
//
//import com.read.write.separated.demo.readwriteseparated.dynamic.datasource.DynamicDataSourceMybatisPlugin;
//import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * Mybatis 插件配置
// * @Author daituo
// * @Date 2019-3-21
// **/
//@Configuration
//public class MybatisPluginConfig {
//
//    @Autowired
//    private DynamicDataSourceMybatisPlugin plugin;
//
//
//    /** 在这里可以通过 configuration 添加自定义的 interceptor*/
//    @Bean
//    public ConfigurationCustomizer configurationCustomizer() {
//        return configuration -> {
//            configuration.addInterceptor(plugin);
//        };
//    }
//}
