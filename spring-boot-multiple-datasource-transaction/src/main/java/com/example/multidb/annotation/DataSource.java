package com.example.multidb.annotation;

import com.example.multidb.constant.DataSourceType;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数据源切换注解，用于在方法或类级别指定使用的数据源
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {

  /**
   * 数据源类型
   */
  DataSourceType value() default DataSourceType.USER;
}
