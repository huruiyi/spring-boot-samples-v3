package com.example.multidb.entity;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

  private Long id;
  private String username;
  private String email;
  private String phone;
  private Integer age;
  private String address;
  private LocalDateTime createTime;
  private LocalDateTime updateTime;
}
