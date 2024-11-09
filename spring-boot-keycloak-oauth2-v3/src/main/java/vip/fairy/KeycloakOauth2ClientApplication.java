package vip.fairy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KeycloakOauth2ClientApplication {

  /**
   * https://github.com/justahmed99/spring-boot-oauth2-client-keycloak
   * 获取code:
   * https://keycloak.fairy.vip/realms/howtodoinjava/protocol/openid-connect/auth?response_type=code&client_id=employee-management-api&scope=openid&redirect_uri=http://localhost:9090/login/oauth2/code/employee-management-api
   */
  public static void main(String[] args) {
    SpringApplication.run(KeycloakOauth2ClientApplication.class, args);
  }

}
