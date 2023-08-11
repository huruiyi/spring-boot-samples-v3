package com.example.springbootjpaoracle.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Review {

  @Id
  @GeneratedValue
  private Long id;

  private String rating;

  private String description;

  @ManyToOne
  private Course course;

  protected Review() {
  }

  public Review(String rating, String description) {
    this.rating = rating;
    this.description = description;
  }

  public void setCourse(Course course) {
    this.course = course;
  }

  @Override
  public String toString() {
    return String.format("Review[%s %s]", rating, description);
  }

}