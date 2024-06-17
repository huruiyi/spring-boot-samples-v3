package vip.fairy.springbootsecurityh2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vip.fairy.springbootsecurityh2.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
