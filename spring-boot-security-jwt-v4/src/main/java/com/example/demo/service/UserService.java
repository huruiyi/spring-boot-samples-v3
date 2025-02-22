package com.example.demo.service;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final PasswordEncoder passwordEncoder;


  public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @PostConstruct
  public void init(){
    Role roleAdmin = new Role();
    roleAdmin.setName("ADMIN");
    roleRepository.save(roleAdmin);

    Role roleUser = new Role();
    roleUser.setName("USER");
    roleRepository.save(roleUser);

    User admin = new User();
    admin.setUsername("admin");
    admin.setPassword(passwordEncoder.encode("admin"));
    admin.setRoles(new HashSet<>(Set.of(roleAdmin)));
    userRepository.save(admin);

    User user = new User();
    user.setUsername("user");
    user.setPassword(passwordEncoder.encode("user"));
    user.setRoles(new HashSet<>(Set.of(roleUser)));
    userRepository.save(user);
  }
}
