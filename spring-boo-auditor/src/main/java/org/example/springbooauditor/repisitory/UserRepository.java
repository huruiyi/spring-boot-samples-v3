package org.example.springbooauditor.repisitory;

import org.example.springbooauditor.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
