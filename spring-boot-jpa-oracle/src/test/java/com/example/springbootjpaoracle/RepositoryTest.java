package com.example.springbootjpaoracle;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.example.springbootjpaoracle.entity.Course;
import com.example.springbootjpaoracle.entity.Passport;
import com.example.springbootjpaoracle.entity.Review;
import com.example.springbootjpaoracle.entity.Student;
import com.example.springbootjpaoracle.repository.CourseRepository;
import com.example.springbootjpaoracle.repository.PassportRepository;
import com.example.springbootjpaoracle.repository.ReviewRepository;
import com.example.springbootjpaoracle.repository.StudentV2Repository;
import com.example.springbootjpaoracle.repository.UserStoredProcedureRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@SpringBootTest(classes = JpaOracleApplication.class)
class RepositoryTest {

  @Autowired
  CourseRepository repository;
  @Autowired
  ReviewRepository reviewRepository;
  @Autowired
  StudentV2Repository studentV2Repository;
  @Autowired
  PassportRepository passportRepository;


  @Test
  void findAllTest() {
    Iterable<Review> reviews = reviewRepository.findAll();
    reviews.forEach(item -> log.info(item.toString()));
    Assertions.assertNotNull(reviews);
  }

  @Test
  void findAllV2Test() {
    Iterable<Review> reviews = reviewRepository.findAll(Sort.by(Direction.DESC, "rating"));
    reviews.forEach(item -> log.info(item.toString()));
    Assertions.assertNotNull(reviews);
  }

  @Test
  void findAllV3Test() {
    //Pageable pageable = Pageable.ofSize(5);
    Pageable pageable = PageRequest.of(2, 5, Sort.by(Direction.DESC, "rating"));
    Iterable<Review> reviews = reviewRepository.findAll(pageable);
    reviews.forEach(item -> log.info(item.toString()));
    Assertions.assertNotNull(reviews);
  }

  @Test
  void pageTest() {
    Specification<Review> specification = (root, query, criteriaBuilder) -> {
      List<Predicate> predicatesList = new ArrayList<>();

      Path<Review> path = root.get("description");
      Predicate equal = criteriaBuilder.equal(path, "7");
      log.debug(equal.toString());

      Path<Review> description = root.get("description");
      Predicate like = criteriaBuilder.like(description.as(String.class), "1%");
      log.debug(like.toString());

      predicatesList.add(criteriaBuilder.like(root.get("description"), "1%"));
      predicatesList.add(criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get("rating"), "10")));

      return criteriaBuilder.and(predicatesList.toArray(new Predicate[0]));
    };
    Pageable pageable = PageRequest.of(2, 3, Sort.by(Direction.DESC, "rating"));
    Iterable<Review> reviews = reviewRepository.findAll(specification, pageable);
    reviews.forEach(item -> log.info(item.toString()));
    Assertions.assertNotNull(reviews);
  }


  @Test
  @Transactional
  void retrieveStudentAndPassportDetails_retrievePassportAndAssociatedStudent() {
    //通过学生找身份证信息
    Optional<Student> optionalStudent = studentV2Repository.findById(20001L);
    optionalStudent.ifPresent(student -> {
      log.info("1-student -> {}", student);
      log.info("1-passport -> {}", student.getPassport());
    });
    Assertions.assertTrue(optionalStudent.isPresent());

    //通过身份证找学生信息
    Optional<Passport> optionalPassport = passportRepository.findById(40001L);
    optionalPassport.ifPresent(passport -> {
      log.info("2-passport -> {}", passport);
      log.info("2-student -> {}", passport.getStudent());
    });
    Assertions.assertTrue(optionalPassport.isPresent());
  }

  @Test
  @Transactional
  void retrieveStudentAndCoursesTest() {
    Optional<Student> optionalStudent = studentV2Repository.findById(20001L);
    optionalStudent.ifPresent(student -> {
      log.info("student -> {}", student);
      List<Course> courses = student.getCourses();
      courses.forEach(course -> {
        log.info("--course -> {}", course);
        List<Review> reviews = course.getReviews();
        reviews.forEach(review -> {
          log.info("------review -> {}", review);
        });
      });
    });
    Assertions.assertTrue(optionalStudent.isPresent());
  }

  @Test
  void findById_basic() {
    Course course = repository.findById(10001L);
    assertEquals("JPA in 50 Steps", course.getName());
  }

  @Test
  @DirtiesContext
  void deleteById_basic() {
    repository.deleteById(10002L);
    assertNull(repository.findById(10002L));
  }

  @Test
  @DirtiesContext
  void save_basic() {

    // get a course
    Course course = repository.findById(10001L);
    assertEquals("JPA in 50 Steps", course.getName());

    // update details
    course.setName("JPA in 50 Steps - Updated");
    repository.save(course);

    // check the value
    Course course1 = repository.findById(10001L);
    assertEquals("JPA in 50 Steps - Updated", course1.getName());
  }

}
