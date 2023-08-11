package com.example.springbootjpaoracle.repository;

import com.example.springbootjpaoracle.entity.Course;
import com.example.springbootjpaoracle.entity.Review;
import jakarta.persistence.EntityManager;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Repository
@Transactional
public class CourseRepository {


  @Autowired
  EntityManager em;

  public Course findById(Long id) {
    return em.find(Course.class, id);
  }

  public Course save(Course course) {
    if (course.getId() == null) {
      em.persist(course);
    } else {
      em.merge(course);
    }

    return course;
  }

  public void deleteById(Long id) {
    Course course = findById(id);
    em.remove(course);
  }

  public void playWithEntityManager() {
    Course course1 = new Course("Web Services in 100 Steps");
    em.persist(course1);

    Course course2 = findById(10001L);

    course2.setName("JPA in 50 Steps - Updated");
  }

  public void addHardcodedReviewsForCourse() {
    //get the course 10003
    Course course = findById(10003L);
    log.info("course.getReviews() -> {}", course.getReviews());

    //add 2 reviews to it
    Review review1 = new Review("5", "Great Hands-on Stuff.");
    Review review2 = new Review("5", "Hatsoff.");

    //setting the relationship
    course.addReview(review1);
    review1.setCourse(course);

    course.addReview(review2);
    review2.setCourse(course);

    //save it to the database
    em.persist(review1);
    em.persist(review2);
  }

  public void addReviewsForCourse(Long courseId, List<Review> reviews) {
    Course course = findById(courseId);
    log.info("course.getReviews() -> {}", course.getReviews());
    for (Review review : reviews) {
      course.addReview(review);
      review.setCourse(course);
      em.persist(review);
    }
  }
}