package vip.fairy.springbootsecurityh2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vip.fairy.springbootsecurityh2.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

  User findByUsername(String username);
}
