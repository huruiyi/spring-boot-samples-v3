package com.example.springbootjpaoracle.repository;

import com.example.springbootjpaoracle.entity.Course;
import com.example.springbootjpaoracle.entity.Passport;
import com.example.springbootjpaoracle.entity.Student;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Repository
@Transactional
public class StudentRepository {


  @Autowired
  EntityManager em;

  public Student findById(Long id) {
    return em.find(Student.class, id);
  }

  public Student save(Student student) {
    if (student.getId() == null) {
      em.persist(student);
    } else {
      em.merge(student);
    }
    return student;
  }

  public void deleteById(Long id) {
    Student student = findById(id);
    em.remove(student);
  }

  public void saveStudentWithPassport() {
    Passport passport = new Passport("Z123456");
    em.persist(passport);

    Student student = new Student("Mike");

    student.setPassport(passport);
    em.persist(student);
  }

  public void someOperationToUnderstandPersistenceContext() {
    //Database Operation 1 - Retrieve student
    Student student = em.find(Student.class, 20001L);
    //Persistence Context (student)

    //Database Operation 2 - Retrieve passport
    Passport passport = student.getPassport();
    //Persistence Context (student, passport)

    //Database Operation 3 - update passport
    passport.setNo("E123457");
    //Persistence Context (student, passport++)

    //Database Operation 4 - update student
    student.setName("Ranga - updated");
    //Persistence Context (student++ , passport++)
  }

  public void insertHardcodedStudentAndCourse() {
    Student student = new Student("Jack");
    Course course = new Course("Microservices in 100 Steps");
    em.persist(student);
    em.persist(course);

    student.addCourse(course);
    course.addStudent(student);
    em.persist(student);
  }

  public void insertStudentAndCourse(Student student, Course course) {
    log.info(student.toString());
    student.addCourse(course);
    course.addStudent(student);

    em.persist(student);
    em.persist(course);
  }

}
