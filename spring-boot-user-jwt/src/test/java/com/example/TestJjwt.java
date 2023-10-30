package com.example;

import com.alibaba.fastjson2.JSON;
import com.example.model.User;
import com.example.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.SignatureAlgorithm;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.junit.jupiter.api.Test;

public class TestJjwt {

  protected static byte[] randomKey() {
    byte[] key = new byte[64];
    SecureRandom random = new SecureRandom();
    random.nextBytes(key);
    return key;
  }

  public static void main(String[] args) {
    User user = new User();
    user.setUserID("10");
    user.setUserName("张三");
    user.setPassWord("123123");
    // jwt所面向的用户，放登录的用户名等
    String subject = JSON.toJSONString(user);
    // "Jack"是jwt签发者，"李四"是jwt接收者
    String jwt = JwtUtils.createJWT("Jack", Arrays.asList("张三", "张小三"), subject);
    System.out.println("JWT：" + jwt);
    System.out.println("JWT长度：" + jwt.length());
    System.out.println("\njwt三个组成部分中间payload部分的解密：");
    Claims c = JwtUtils.parseJWT(jwt);
    System.out.println("jti用户id：" + c.getId());
    System.out.println("iat登录时间：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(c.getIssuedAt()));
    System.out.println("iss签发者：" + c.getIssuer());
    System.out.println("sub用户信息列表：" + c.getSubject());
    System.out.println("exp过期时间：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(c.getExpiration()));
    System.out.println("aud接收者：" + c.getAudience());
    System.out.println("登录的用户名：" + c.get("username"));
    // 或
    System.out.println("登录的用户名：" + c.get("username", String.class));
    System.out.println("登录的密码：" + c.get("password", String.class));
  }


  @Test
  void test1() {
    // Create a test key suitable for the desired RSA signature algorithm:
    SignatureAlgorithm alg = Jwts.SIG.RS512; //or PS512, RS256, etc...
    KeyPair pair = alg.keyPair().build();

    // Bob creates the compact JWS with his RSA private key:
    PrivateKey aPrivate = pair.getPrivate();
    String jws = Jwts.builder().subject("Alice")
        .signWith(aPrivate, alg) // <-- Bob's RSA private key
        .compact();

    // Alice receives and verifies the compact JWS came from Bob:
    String subject = Jwts.parser()
        .verifyWith(pair.getPublic()) // <-- Bob's RSA public key
        .build().parseClaimsJws(jws).getPayload().getSubject();

    assert "Alice".equals(subject);
  }

  @Test
  void test2() {
    Map<String, Object> mapClaims = new HashMap<>();
    mapClaims.put("username", "admin");
    mapClaims.put("password", "010203");
    SecretKeySpec secretKeySpec = new SecretKeySpec(Base64.decodeBase64("233453235253453532535425324532577547657345121225"), "HmacSHA256");
    Claims claims = Jwts.claims().add(mapClaims)
        .subject("Joe")
        .issuer("vip.fairy").build();
    String compactJwt = Jwts.builder().claims(claims).signWith(secretKeySpec).compact();
    System.out.println(compactJwt);

    Claims payload = Jwts.parser().verifyWith(secretKeySpec).build().parseClaimsJws(compactJwt).getPayload();
    System.out.println(payload);
  }
}
