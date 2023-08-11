package com.example.springbootjpaoracle.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Student {

  @Id
  @GeneratedValue
  private Long id;

  @Column(nullable = false)
  private String name;

  public Student() {

  }

  public Student(String name) {
    this.name = name;
  }

  public void addCourse(Course course) {
    this.courses.add(course);
  }

  @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinColumn(name = "passport_id")
  private Passport passport;

  @ManyToMany
  @JoinTable(name = "STUDENT_COURSE",
      joinColumns = @JoinColumn(name = "STUDENT_ID"),
      inverseJoinColumns = @JoinColumn(name = "COURSE_ID"))
  private List<Course> courses = new ArrayList<>();



  @Override
  public String toString() {
    return String.format("Student[%s]", name);
  }
}