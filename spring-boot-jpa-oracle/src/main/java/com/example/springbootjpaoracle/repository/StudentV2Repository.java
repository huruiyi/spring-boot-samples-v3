package com.example.springbootjpaoracle.repository;

import com.example.springbootjpaoracle.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface StudentV2Repository extends JpaRepository<Student, Long>, JpaSpecificationExecutor<Student> {

}
