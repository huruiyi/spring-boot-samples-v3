package com.example.multidb.config;

import com.example.multidb.datasource.DynamicDataSource;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据源配置类
 */
@Configuration
public class DataSourceConfig {

    /**
     * 用户数据源配置
     */
    @Bean("userDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.user")
    public DataSource userDataSource() {
        return new HikariDataSource();
    }

    /**
     * 订单数据源配置
     */
    @Bean("orderDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.order")
    public DataSource orderDataSource() {
        return new HikariDataSource();
    }

    /**
     * 动态数据源配置
     */
    @Bean
    @Primary
    public DataSource dynamicDataSource(DataSource userDataSource, DataSource orderDataSource) {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put("user", userDataSource);
        targetDataSources.put("order", orderDataSource);
        
        dynamicDataSource.setTargetDataSources(targetDataSources);
        dynamicDataSource.setDefaultTargetDataSource(userDataSource);
        
        return dynamicDataSource;
    }
}
