package com.example;

import com.example.model.auth.RegisterRequest;
import com.example.model.user.Role;
import com.example.service.AuthenticationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class SecurityApplication {

  public static void main(String[] args) {
    SpringApplication.run(SecurityApplication.class, args);
  }

  @Bean
  public CommandLineRunner commandLineRunner(AuthenticationService service) {
    return args -> {
      RegisterRequest registerRequestAdmin = RegisterRequest.builder()
          .firstname("Admin")
          .lastname("Admin")
          .email("admin@mail.com")
          .password("password")
          .role(Role.ADMIN)
          .build();
      System.out.println("Admin token: " + service.register(registerRequestAdmin).getAccessToken());

      RegisterRequest registerRequestManager = RegisterRequest.builder()
          .firstname("Admin")
          .lastname("Admin")
          .email("manager@mail.com")
          .password("password")
          .role(Role.MANAGER)
          .build();
      System.out.println("Manager token: " + service.register(registerRequestManager).getAccessToken());

    };
  }
}
