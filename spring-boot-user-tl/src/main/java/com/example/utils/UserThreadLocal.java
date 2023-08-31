package com.example.utils;

import com.example.model.UserInfo;

public class UserThreadLocal {

  /**
   * 构造函数私有
   */
  private UserThreadLocal() {
  }

  private static final ThreadLocal<UserInfo> USER_INFO_THREAD_LOCAL =
      new ThreadLocal<>();

  /**
   * 清除用户信息
   */
  public static void clear() {
    USER_INFO_THREAD_LOCAL.remove();
  }

  /**
   * 存储用户信息
   */
  public static void set(UserInfo userDTO) {
    USER_INFO_THREAD_LOCAL.set(userDTO);
  }

  /**
   * 获取当前用户信息
   */
  public static UserInfo getCurrentUser() {
    return USER_INFO_THREAD_LOCAL.get();
  }
}
