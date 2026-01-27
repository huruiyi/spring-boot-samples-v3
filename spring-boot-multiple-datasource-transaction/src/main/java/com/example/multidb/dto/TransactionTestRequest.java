package com.example.multidb.dto;

import lombok.Data;

/**
 * 事务测试请求参数
 */
@Data
public class TransactionTestRequest {

  /**
   * 是否回滚事务（true: 抛出异常测试回滚，false: 正常提交）
   */
  private Boolean rollback;

  /**
   * 用户名
   */
  private String username;

  /**
   * 邮箱
   */
  private String email;

  /**
   * 电话
   */
  private String phone;

  /**
   * 订单号
   */
  private String orderNo;

  /**
   * 产品名称
   */
  private String productName;

  /**
   * 数量
   */
  private Integer quantity;

  /**
   * 总价格
   */
  private Double totalPrice;
}
