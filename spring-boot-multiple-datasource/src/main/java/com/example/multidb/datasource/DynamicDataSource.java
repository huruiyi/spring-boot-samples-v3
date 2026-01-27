package com.example.multidb.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 动态数据源，继承 AbstractRoutingDataSource 实现
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return com.example.multidb.context.DataSourceContextHolder.getDataSource();
    }
}
