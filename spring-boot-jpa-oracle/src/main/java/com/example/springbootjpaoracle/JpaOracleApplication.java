package com.example.springbootjpaoracle;

import com.example.springbootjpaoracle.repository.CourseRepository;
import com.example.springbootjpaoracle.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class JpaOracleApplication implements CommandLineRunner {

  private Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired
  private CourseRepository courseRepository;

  @Autowired
  private StudentRepository studentRepository;

  public static void main(String[] args) {
    SpringApplication.run(JpaOracleApplication.class, args);
  }

  @Override
  public void run(String... arg0) throws Exception {
    //studentRepository.saveStudentWithPassport();
    //courseRepository.playWithEntityManager();
  }
}
