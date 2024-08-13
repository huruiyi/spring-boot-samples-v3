package vip.fairy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AuthorizationServerSimpleApplicationV2 {

    /**
    * <p>
   * @param args
   */
  public static void main(String[] args) {
    SpringApplication.run(AuthorizationServerSimpleApplicationV2.class, args);
  }

}

/*
 client_secret_post:client_id和client_secret放到请求体

 curl --location 'http://localhost:8080/oauth2/token' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'grant_type=client_credentials' \
--data-urlencode 'scope=openid' \
--data-urlencode 'client_id=oidc-client' \
--data-urlencode 'client_secret=secret'
--------------------------------------------------------------------------------------------------
 client_secret_basic：client_id和client_secret放到请求头（Basic Auth）
		 Authorization:Basic base64(client_id:client_secret)

 curl --location 'http://localhost:8080/oauth2/token' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--header 'Authorization: Basic b2lkYy1jbGllbnQ6c2VjcmV0' \
--header 'Cookie: JSESSIONID=6E804008DADC5E3A893A01119FF625F3' \
--data-urlencode 'grant_type=client_credentials' \
--data-urlencode 'scope=openid'

curl --location 'http://localhost:8080/oauth2/token' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--header 'Authorization: Basic b2lkYy1jbGllbnQ6c2VjcmV0' \
--data-urlencode 'grant_type=authorization_code' \
--data-urlencode 'code=VoALvwD_HRXj8nEet9G1dRUiamDMAxz18Ox7F-7-VcSizPAQFJYY3r1i8s4BhRaaUlCzbTiKQK-X1UmkRzevkdvBzum1YF9Y1YunFvs88sUl4ijs-56ruDSF9srR5k0a' \
--data-urlencode 'redirect_uri=http://127.0.0.1:8080/login/oauth2/code/oidc-client'

 */
