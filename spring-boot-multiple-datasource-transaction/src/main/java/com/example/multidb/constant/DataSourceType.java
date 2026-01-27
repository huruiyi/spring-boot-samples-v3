package com.example.multidb.constant;

/**
 * 数据源类型枚举
 */
public enum DataSourceType {
  USER("user", "用户数据源"),
  ORDER("order", "订单数据源");

  private final String code;
  private final String description;

  DataSourceType(String code, String description) {
    this.code = code;
    this.description = description;
  }

  public String getCode() {
    return code;
  }

  public String getDescription() {
    return description;
  }
}
