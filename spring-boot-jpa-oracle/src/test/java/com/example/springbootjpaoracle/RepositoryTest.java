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
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(classes = JpaOracleApplication.class)
public class RepositoryTest {

  private Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired
  CourseRepository repository;

  @Autowired
  ReviewRepository reviewRepository;

  @Autowired
  StudentV2Repository studentV2Repository;

  @Autowired
  PassportRepository passportRepository;

  @Autowired
  UserStoredProcedureRepository userStoredProcedureRepository;


  @Autowired
  EntityManager em;

  @Test
  void findAll() {
    Iterable<Review> reviews = reviewRepository.findAll();
    reviews.forEach(item -> {
      System.out.println(item.toString());
    });
  }

  @Test
  void findAll_V2() {
    Iterable<Review> reviews = reviewRepository.findAll(Sort.by(Direction.DESC, "rating"));
    reviews.forEach(item -> {
      System.out.println(item.toString());
    });
  }

  @Test
  void findAll_V3() {
    //Pageable pageable = Pageable.ofSize(5);
    Pageable pageable = PageRequest.of(2, 5, Sort.by(Direction.DESC, "rating"));
    Iterable<Review> reviews = reviewRepository.findAll(pageable);
    reviews.forEach(item -> {
      System.out.println(item.toString());
    });
  }

  @Test
  public void pageTest() {
    Specification<Review> specification = (root, query, criteriaBuilder) -> {
      List<Predicate> predicatesList = new ArrayList<>();

//        Path<Review> path = root.get("description");
//        Predicate equal = criteriaBuilder.equal(path, "7");

//        Path<Review> description = root.get("description");
//        Predicate like = criteriaBuilder.like(description.as(String.class), "1%");

      predicatesList.add(criteriaBuilder.like(root.get("description"), "1%"));
      predicatesList.add(criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get("rating"), "10")));

      return criteriaBuilder.and(predicatesList.toArray(new Predicate[predicatesList.size()]));
    };
    Pageable pageable = PageRequest.of(2, 3, Sort.by(Direction.DESC, "rating"));
    Iterable<Review> reviews = reviewRepository.findAll(specification, pageable);
    reviews.forEach(item -> {
      System.out.println(item.toString());
    });
  }


  @Test
  @Transactional
  public void retrieveStudentAndPassportDetails_retrievePassportAndAssociatedStudent() {
    //通过学生找身份证信息
    Student student = studentV2Repository.findById(20001L).get();
    logger.info("1-student -> {}", student);
    logger.info("1-passport -> {}", student.getPassport());

    //通过身份证找学生信息
    Passport passport = passportRepository.findById(40001l).get();
    logger.info("2-passport -> {}", passport);
    logger.info("2-student -> {}", passport.getStudent());
  }

  @Test
  @Transactional
  public void retrieveStudentAndCourses() {
    Student student = studentV2Repository.findById(20001L).get();
    logger.info("student -> {}", student);
    List<Course> courses = student.getCourses();
    courses.forEach(course -> {
      logger.info("--course -> {}", course);
      List<Review> reviews = course.getReviews();
      reviews.forEach(review -> {
        logger.info("------review -> {}", review);
      });
    });
  }

  @Test
  public void findById_basic() {
    Course course = repository.findById(10001L);
    assertEquals("JPA in 50 Steps", course.getName());
  }

  @Test
  @DirtiesContext
  public void deleteById_basic() {
    repository.deleteById(10002L);
    assertNull(repository.findById(10002L));
  }

  @Test
  @DirtiesContext
  public void save_basic() {

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

  @Test
  public void test_Procedure(){
    assertThat(userStoredProcedureRepository.plus1BackedByOtherNamedStoredProcedure(1)).isEqualTo(2);
    assertThat(userStoredProcedureRepository.plus1inout(1)).isEqualTo(2);
  }

  @Test
  void plainJpa21() {
    var proc = em.createStoredProcedureQuery("plus1inout");
    proc.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
    proc.registerStoredProcedureParameter(2, Integer.class, ParameterMode.OUT);

    proc.setParameter(1, 1);
    proc.execute();

    assertThat(proc.getOutputParameterValue(2)).isEqualTo(2);
  }

  @Test
  void plainJpa21_entityAnnotatedCustomNamedProcedurePlus1IO() {
    var proc = em.createNamedStoredProcedureQuery("User.plus1");

    proc.setParameter("arg", 1);
    proc.execute();

    assertThat(proc.getOutputParameterValue("res")).isEqualTo(2);
  }
}
