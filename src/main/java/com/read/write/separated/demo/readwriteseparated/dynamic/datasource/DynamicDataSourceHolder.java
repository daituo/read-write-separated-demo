package com.read.write.separated.demo.readwriteseparated.dynamic.datasource;

/**
 * 定义当前线程中的数据源
 * @Author daituo
 * @Date 2019-3-21
 **/
public class DynamicDataSourceHolder {

    /** 线程变量封装当前线程拥有的DataSource的LookupKey */
    private static final ThreadLocal<DynamicDataSourceGlobal> holder = new ThreadLocal<DynamicDataSourceGlobal>();

    private DynamicDataSourceHolder() {
    }

    public static void putDataSource(DynamicDataSourceGlobal dataSource) {
        holder.set(dataSource);
    }

    public static DynamicDataSourceGlobal getDataSource() {
        return holder.get();
    }

    public static void clearDataSource() {
        holder.remove();
    }

}
