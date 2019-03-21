package com.read.write.separated.demo.readwriteseparated.dynamic.datasource;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * mybatis plugin,用于判断sql，如果是select则需要切换到读数据源
 *
 * @Author daituo
 * @Date 2019-3-21
 **/
@Intercepts({
    /** 只有符合签名的才会执行拦截逻辑，type-拦截类  method-拦截方法 */
    @Signature(
            type = Executor.class,
            method = "update",
            args = {MappedStatement.class,Object.class}),
    @Signature(
            type = Executor.class,
            method = "query",
            args = {MappedStatement.class,Object.class,RowBounds.class,ResultHandler.class,CacheKey.class,BoundSql.class})
})
@Slf4j
public class DynamicDataSourceMybatisPlugin implements Interceptor {

    /** 该正则用于匹配sql语句中是否包含非select关键词 */
    private static final String regex = "\\.*insert&|\\.*delete&|\\.*update&";

    /** 把MappedStatementID 和对应的DynamicDataSourceGlobal 缓存起来*/
    private static final Map<String,DynamicDataSourceGlobal> cacheMap = new ConcurrentHashMap<>();

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        /** 如果当前拥有事务上下文，则将连接绑定到事务上下文中*/
        boolean transactionActive = TransactionSynchronizationManager.isActualTransactionActive();
        if (!transactionActive) {
            Object[] args = invocation.getArgs();
            //从签名中可以，第一个参数是MappedStatement
            MappedStatement mappedStatement = (MappedStatement) args[0];
            DynamicDataSourceGlobal dynamicDataSourceGlobal = cacheMap.get(mappedStatement.getId());
            String sql = null;
            if (dynamicDataSourceGlobal == null) {
                if (mappedStatement.getSqlCommandType().equals(SqlCommandType.SELECT)) {
                    BoundSql boundSql = mappedStatement.getSqlSource().getBoundSql(args[1]);
                    /** 获取sql语句，并替换回车换行 \t为制表符 \n为换行 \r为回车*/
                    sql = boundSql.getSql().toLowerCase().replaceAll("\\t\\n\\r", " ");
                    if (sql.matches(regex)) {
                        dynamicDataSourceGlobal = DynamicDataSourceGlobal.WRITE;
                    } else {
                        dynamicDataSourceGlobal = DynamicDataSourceGlobal.READ;
                    }
                } else {
                    /** 如果不是select，直接切换到写库*/
                    dynamicDataSourceGlobal = DynamicDataSourceGlobal.WRITE;
                }
                cacheMap.put(mappedStatement.getId(),dynamicDataSourceGlobal);
                log.info("MappedStatementId:{},sql:{},切换到:{}数据源",mappedStatement.getId(),sql,dynamicDataSourceGlobal);
            }
            DynamicDataSourceHolder.putDataSource(dynamicDataSourceGlobal);
        }
        return invocation.proceed();
    }

    /**
     * @param target 被代理的对象
     * @return 用该自定义拦截器包裹的代理对象
     */
    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target,this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
