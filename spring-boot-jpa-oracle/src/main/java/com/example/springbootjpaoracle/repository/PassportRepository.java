package com.example.springbootjpaoracle.repository;

import com.example.springbootjpaoracle.entity.Passport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface PassportRepository extends JpaRepository<Passport, Long>, JpaSpecificationExecutor<Passport> {

}
