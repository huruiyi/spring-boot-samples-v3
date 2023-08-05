package com.example.demo.utils;

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
    byte[] encodedKey = Base64.decodeBase64(JWT_SECRET);
    return new SecretKeySpec(encodedKey, 0, encodedKey.length, "HmacSHA256");
  }

  // 创建jwt
  public static String createJWT(String issuer, String audience, String subject) throws Exception {
    // 设置头部信息
//		Map<String, Object> header = new HashMap<String, Object>();
//		header.put("typ", "JWT");
//		header.put("alg", "HS256");
    // 或
    // 指定header那部分签名的时候使用的签名算法，jjwt已经将这部分内容封装好了，只有{"alg":"HS256"}
    SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
    Map<String, Object> claims = new HashMap<>();
    claims.put("username", "admin");
    claims.put("password", "010203");

    long nowTime = System.currentTimeMillis();
    Date issuedAt = new Date(nowTime);
    long exp = nowTime + EXPIRE_TIME;

    SecretKey key = generalKey();
    JwtBuilder builder = Jwts.builder()
//		  .setHeader(header)
        .setClaims(claims)
        .setId(JWT_ID)
        .setIssuedAt(issuedAt)
        .setIssuer(issuer)
        .setSubject(subject)
        .signWith(signatureAlgorithm, key);
    builder.setExpiration(new Date(exp));
    if (audience == null || audience.isEmpty()) {
      builder.setAudience("Tom");
    } else {
      builder.setAudience(audience);
    }
    return builder.compact();
  }

  // 解密jwt
  public static Claims parseJWT(String jwt) {
    SecretKey key = generalKey();
    //return Jwts.parser().setSigningKey(key).parseClaimsJws(jwt).getBody();
    return Jwts.parserBuilder().verifyWith(key).build().parseClaimsJws(jwt).getPayload();
  }

}