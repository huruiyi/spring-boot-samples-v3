package com.mycompany.generic.repository;

import com.mycompany.generic.entity.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

  Optional<UserEntity> findByEmail(String email);

  Boolean existsByEmail(String email);

}
