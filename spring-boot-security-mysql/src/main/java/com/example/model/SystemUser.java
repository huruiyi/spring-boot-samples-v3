package com.example.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("user")
public class SystemUser {

  private Integer id;
   private String username;
  private String password;
}
