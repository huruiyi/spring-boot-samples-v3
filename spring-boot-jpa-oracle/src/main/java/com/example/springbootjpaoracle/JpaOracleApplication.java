package com.example.springbootjpaoracle;

import com.example.springbootjpaoracle.entity.Course;
import com.example.springbootjpaoracle.repository.CourseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class JpaOracleApplication implements CommandLineRunner {

  @Autowired
  private CourseRepository courseRepository;


  public static void main(String[] args) {
    SpringApplication.run(JpaOracleApplication.class, args);
  }

  @Override
  public void run(String... arg0) throws Exception {
    Course course = courseRepository.findById(10001L);
    log.info(course.toString());
  }
}
