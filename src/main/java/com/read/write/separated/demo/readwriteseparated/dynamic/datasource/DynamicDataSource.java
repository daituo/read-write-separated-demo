package com.read.write.separated.demo.readwriteseparated.dynamic.datasource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 继承AbstractRoutingDataSource类，来实现spring 动态切换数据源
 * @Author daituo
 * @Date 2019-3-21
 **/
@Slf4j
public class DynamicDataSource extends AbstractRoutingDataSource {

    /** spring 根据返回的LookupKey，去数据源容器中resolvedDataSources取出对应的数据源 */
    @Override
    protected Object determineCurrentLookupKey() {
        /** 获取当前线程拥有的DataSource的LookupKey*/
        DynamicDataSourceGlobal lookupKey = DynamicDataSourceHolder.getDataSource();
        if (lookupKey == null || lookupKey == DynamicDataSourceGlobal.WRITE) {
            log.info("切换到: {} 数据源",DynamicDataSourceGlobal.WRITE.name());
            return DynamicDataSourceGlobal.WRITE.name();
        }
        log.info("切换到: {} 数据源",DynamicDataSourceGlobal.READ.name());
        return DynamicDataSourceGlobal.READ.name();
    }
}
