package vip.fairy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisIndexedHttpSession;

@SpringBootApplication
@EnableRedisIndexedHttpSession
public class FindByUsernameApplication {

  public static void main(String[] args) {
    SpringApplication.run(FindByUsernameApplication.class, args);
  }

}
