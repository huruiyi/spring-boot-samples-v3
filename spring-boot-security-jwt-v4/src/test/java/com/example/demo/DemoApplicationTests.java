package com.example.demo;

import java.security.Key;
import org.jose4j.jwa.AlgorithmConstraints;
import org.jose4j.jwa.AlgorithmConstraints.ConstraintType;
import org.jose4j.jwe.ContentEncryptionAlgorithmIdentifiers;
import org.jose4j.jwe.JsonWebEncryption;
import org.jose4j.jwe.KeyManagementAlgorithmIdentifiers;
import org.jose4j.keys.AesKey;
import org.jose4j.lang.ByteUtil;
import org.jose4j.lang.JoseException;
import org.junit.jupiter.api.Test;

class DemoApplicationTests {

  String aes128CbcHmacSha256 = ContentEncryptionAlgorithmIdentifiers.AES_256_CBC_HMAC_SHA_512;
  String a128kw = KeyManagementAlgorithmIdentifiers.A128KW;

  @Test
  void contextLoads() throws JoseException {
    Key key = new AesKey(ByteUtil.randomBytes(16));
    JsonWebEncryption jwe = new JsonWebEncryption();
    jwe.setPayload("Hello World!世界你好！！");
    jwe.setAlgorithmHeaderValue(a128kw);
    jwe.setEncryptionMethodHeaderParameter(aes128CbcHmacSha256);
    jwe.setKey(key);
    String serializedJwe = jwe.getCompactSerialization();
    System.out.println("Serialized Encrypted JWE: " + serializedJwe);

    jwe = new JsonWebEncryption();
    jwe.setAlgorithmConstraints(new AlgorithmConstraints(ConstraintType.PERMIT, a128kw));
    jwe.setContentEncryptionAlgorithmConstraints(new AlgorithmConstraints(ConstraintType.PERMIT, aes128CbcHmacSha256));
    jwe.setKey(key);
    jwe.setCompactSerialization(serializedJwe);
    System.out.println("Payload: " + jwe.getPayload());
  }

}
