package com.example.entities;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("jwt_user")
public class JwtUser {

  @TableId
  private String userId;

  private String userName;

  private String password;

}
