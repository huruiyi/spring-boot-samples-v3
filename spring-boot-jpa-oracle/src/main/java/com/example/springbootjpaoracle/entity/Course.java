package com.example.springbootjpaoracle.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


@Getter
@Setter
@Entity
@NamedQueries(value = {
    @NamedQuery(name = "query_get_all_courses", query = "Select  c  From Course c"),
    @NamedQuery(name = "query_get_100_Step_courses", query = "Select  c  From Course c where name like '%100 Steps'")
})
public class Course {

  @Id
  @GeneratedValue
  private Long id;

  @Column(nullable = false)
  private String name;

  @OneToMany(mappedBy = "course")
  private List<Review> reviews = new ArrayList<>();

  @ManyToMany(mappedBy = "courses")
  private List<Student> students = new ArrayList<>();

  @UpdateTimestamp
  private LocalDateTime lastUpdatedDate;

  @CreationTimestamp
  private LocalDateTime createdDate;

  protected Course() {
  }

  public Course(String name) {
    this.name = name;
  }

  public void addReview(Review review) {
    this.reviews.add(review);
  }

  public void addStudent(Student student) {
    this.students.add(student);
  }

  @Override
  public String toString() {
    return String.format("Course[%s]", name);
  }
}
