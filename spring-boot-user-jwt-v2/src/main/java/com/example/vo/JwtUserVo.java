package com.example.vo;

import lombok.Data;

@Data
public class JwtUserVo {
  String id;
  private String userName;
  String password;
}
