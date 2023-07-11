package com.example.springbootjpaoracle;


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

@SpringBootTest(classes = DemoApplication.class)
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

  @Test
  public void pageTest() {

//    1:
    // Iterable<Review> reviews = reviewRepository.findAll();

    //2:
    // Iterable<Review>   reviews = reviewRepository.findAll(Sort.by(Direction.DESC, "rating"));

    //3:
    //Pageable pageable = Pageable.ofSize(5);
//    Pageable pageable = PageRequest.of(2, 5, Sort.by(Direction.DESC, "rating"));
//    Iterable<Review> reviews = reviewRepository.findAll(pageable);
//    reviews.forEach(item->{
//      System.out.println(item.toString());
//    });
//

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


  /**
   * 一对一测试:通过学生找身份证信息
   */
  @Test
  @Transactional
  public void retrieveStudentAndPassportDetails() {
    Student student = studentV2Repository.findById(20001L).get();
    logger.info("student -> {}", student);
    logger.info("passport -> {}", student.getPassport());
  }

  /**
   * 一对一测试:通过身份证找学生信息
   */
  @Test
  @Transactional
  public void retrievePassportAndAssociatedStudent() {
    Passport passport = passportRepository.findById(40001l).get();
    logger.info("passport -> {}", passport);
    logger.info("student -> {}", passport.getStudent());
  }


  @Test
  @Transactional
  public void retrieveStudentAndCourses() {
    Student student = studentV2Repository.findById(20001L).get();
    logger.info("student -> {}", student);
    logger.info("courses -> {}", student.getCourses());
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
}
