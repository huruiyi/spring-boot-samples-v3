package com.example.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.apache.tomcat.util.codec.binary.Base64;

// 使用io.jsonwebtoken包
public class JwtUtils {

  private JwtUtils() {
    throw new IllegalStateException("Utility class");
  }

  public static final String JWT_ID = UUID.randomUUID().toString();

  public static final String JWT_SECRET = "233453235253453532535425324532577547657345121225";

  public static final int EXPIRE_TIME = 60 * 60 * 1000; // 一个小时
  //public static final long EXPIRE_TIME = 7 * 24 * 3600 * 1000; // 一个星期

  public static SecretKey generalKey() {
    return new SecretKeySpec(Base64.decodeBase64(JWT_SECRET), "HmacSHA256");
  }

  public static String createJWT(String issuer, String audience, String subject) {
    Map<String, Object> mapClaims = new HashMap<>();
    mapClaims.put("username", "admin");
    mapClaims.put("password", "010203");

    long nowTime = System.currentTimeMillis();
    Date issuedAt = new Date(nowTime);
    long exp = nowTime + EXPIRE_TIME;

    Claims claims = Jwts
        .claims().add(mapClaims)
        .expiration(new Date(exp))
        .id(JWT_ID)
        .issuedAt(issuedAt)
        .issuer(issuer)
        .audience(audience)
        .subject(subject).build();
    return Jwts.builder().claims(claims).signWith(generalKey()).compact();
  }

  // 解密jwt
  public static Claims parseJWT(String jwt) {
     return Jwts.parser().verifyWith(generalKey()).build().parseClaimsJws(jwt).getPayload();
  }

}
