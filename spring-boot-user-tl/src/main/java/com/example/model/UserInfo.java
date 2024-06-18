package com.example.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class UserInfo {

  private Long userId;

  private String userName;

}
