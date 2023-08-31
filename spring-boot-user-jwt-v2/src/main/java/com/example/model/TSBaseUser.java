package com.example.model;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("ts_base_user")
public class TSBaseUser {

  private String id;

  private String userName;

  private String password;

}
