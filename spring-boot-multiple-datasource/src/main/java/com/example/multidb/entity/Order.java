package com.example.multidb.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    
    private Long id;
    private String orderNo;
    private Long userId;
    private String productName;
    private Integer quantity;
    private BigDecimal totalPrice;
    private String status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
