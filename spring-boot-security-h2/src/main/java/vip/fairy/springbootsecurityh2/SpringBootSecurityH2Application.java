package vip.fairy.springbootsecurityh2;

import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import vip.fairy.springbootsecurityh2.model.Role;
import vip.fairy.springbootsecurityh2.model.User;
import vip.fairy.springbootsecurityh2.repository.RoleRepository;
import vip.fairy.springbootsecurityh2.repository.UserRepository;

@SpringBootApplication
public class SpringBootSecurityH2Application implements CommandLineRunner {


  @Autowired
  UserRepository userRepository;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  PasswordEncoder passwordEncoder;

  /*
      #http://localhost:8080/h2-console/
   */
  public static void main(String[] args) {
    SpringApplication.run(SpringBootSecurityH2Application.class, args);
  }

  @Override
  public void run(String... args) {
    createUser();
  }

  private void createUser() {
    Role role = Role.builder().name("USER").build();
    roleRepository.save(role);

    Set<Role> roleList = new HashSet<>();
    roleList.add(role);
    userRepository.save(User.builder().roles(roleList).username("user").password(passwordEncoder.encode("user")).build());
  }

  private void createAdminUser() {
    Role role = Role.builder().name("ADMIN").build();
    roleRepository.save(role);

    Set<Role> roleList = new HashSet<>();
    roleList.add(role);
    userRepository.save(User.builder().roles(roleList).username("admin").password(passwordEncoder.encode("admin")).build());
  }

}
