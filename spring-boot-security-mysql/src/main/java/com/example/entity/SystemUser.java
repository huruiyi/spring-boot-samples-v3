package com.example.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("user")
public class SystemUser {

  private Long id;

  private String username;

  private String password;
}
