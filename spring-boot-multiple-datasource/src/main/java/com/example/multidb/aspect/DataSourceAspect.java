package com.example.multidb.aspect;

import com.example.multidb.annotation.DataSource;
import com.example.multidb.constant.DataSourceType;
import com.example.multidb.context.DataSourceContextHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 数据源切换AOP切面
 */
@Aspect
@Component
@Order(-1)
public class DataSourceAspect {

    private static final Logger logger = LoggerFactory.getLogger(DataSourceAspect.class);

    @Around("@annotation(com.example.multidb.annotation.DataSource) || @within(com.example.multidb.annotation.DataSource)")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        
        DataSource dataSource = method.getAnnotation(DataSource.class);
        if (dataSource == null) {
            dataSource = method.getDeclaringClass().getAnnotation(DataSource.class);
        }

        if (dataSource != null) {
            DataSourceType dataSourceType = dataSource.value();
            DataSourceContextHolder.setDataSource(dataSourceType);
            logger.debug("切换到数据源: {}", dataSourceType.getDescription());
        }

        try {
            return point.proceed();
        } finally {
            DataSourceContextHolder.clearDataSource();
            logger.debug("清除数据源上下文");
        }
    }
}
