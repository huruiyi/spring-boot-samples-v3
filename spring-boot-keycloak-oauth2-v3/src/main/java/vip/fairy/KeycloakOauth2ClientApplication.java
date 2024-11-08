package vip.fairy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KeycloakOauth2ClientApplication {

  /**
   * https://github.com/justahmed99/spring-boot-oauth2-client-keycloak
   */
  public static void main(String[] args) {
    SpringApplication.run(KeycloakOauth2ClientApplication.class, args);
  }

}
