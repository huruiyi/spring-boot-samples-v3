package com.howtodoinjava.app;

import com.howtodoinjava.app.entity.Employee;
import com.howtodoinjava.app.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KeycloakOAuth2ApiApplication implements CommandLineRunner {

  @Autowired
  EmployeeRepository employeeRepository;

  /**
   * https://github.com/lokeshgupta1981/Spring-Boot-Security-OAuth2.git
   */
  public static void main(String[] args) {
    SpringApplication.run(KeycloakOAuth2ApiApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    employeeRepository.save(
        new Employee(1L, "Lokesh", "123456", "admin@howtodoinjava", "Author", "Java Learner")
    );

    employeeRepository.save(
        new Employee(2L, "Alex", "999999", "info@howtodoinjava", "Author", "Another Java Learner")
    );
  }
}
