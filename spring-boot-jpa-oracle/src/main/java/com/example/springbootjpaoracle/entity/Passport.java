package com.example.springbootjpaoracle.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Passport {

  @Id
  @GeneratedValue
  private Long id;

  @Column(nullable = false)
  private String no;

  @OneToOne(fetch = FetchType.LAZY,mappedBy = "passport")
  private Student student;

  protected Passport() {
  }

  public Passport(String no) {
    this.no = no;
  }

  public String getNo() {
    return no;
  }

  public void setNo(String no) {
    this.no = no;
  }

  public Student getStudent() {
    return student;
  }

  public void setStudent(Student student) {
    this.student = student;
  }

  public Long getId() {
    return id;
  }

  @Override
  public String toString() {
    return String.format("Passport[%s]", no);
  }
}