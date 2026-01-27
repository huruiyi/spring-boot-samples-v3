package com.example.multidb.context;

import com.example.multidb.constant.DataSourceType;

/**
 * 数据源上下文管理器，使用 ThreadLocal 保存当前线程的数据源类型
 */
public class DataSourceContextHolder {

    private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<>();

    /**
     * 设置数据源类型
     */
    public static void setDataSource(String dataSourceType) {
        CONTEXT_HOLDER.set(dataSourceType);
    }

    /**
     * 获取数据源类型
     */
    public static String getDataSource() {
        return CONTEXT_HOLDER.get();
    }

    /**
     * 清除数据源类型
     */
    public static void clearDataSource() {
        CONTEXT_HOLDER.remove();
    }

    /**
     * 设置数据源类型（使用枚举）
     */
    public static void setDataSource(DataSourceType dataSourceType) {
        CONTEXT_HOLDER.set(dataSourceType.getCode());
    }
}
