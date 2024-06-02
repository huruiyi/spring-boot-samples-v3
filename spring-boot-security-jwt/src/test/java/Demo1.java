import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Demo1 {

  private static Key getSignInKey() {
    byte[] keyBytes = Decoders.BASE64.decode("s453534534dgfskjdgndsfksdgsdfgsfd54353543s453534534dgfskjdgndsfksdgsdfgsfd54353543");
    return Keys.hmacShaKeyFor(keyBytes);
  }

  public static void main(String[] args) {

    Map<String, String> extraClaims = new HashMap<>();
    extraClaims.put("username", "admin");
    extraClaims.put("password", "123456");
    String token = Jwts
        .builder()
        .setClaims(extraClaims)
        .setSubject("huruiyi")
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + 1000 * 30))
        .signWith(getSignInKey(), SignatureAlgorithm.HS256)
        .compact();

    System.out.printf(token);
  }
}
