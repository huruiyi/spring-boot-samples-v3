package com.example.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.model.User;
import java.util.Date;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

  /**
   * 过期时间5分钟
   */
  private static final long EXPIRE_TIME = 5 * 60 * 1000;

  public String getToken(User user) {
    Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
    String token = "";
    token = JWT.create().withAudience(user.getUserID()) // 将 user id 保存到 token 里面
        .withExpiresAt(date) //五分钟后token过期
        .sign(Algorithm.HMAC256(user.getPassWord())); // 以 password 作为 token 的密钥
    return token;
  }
}
