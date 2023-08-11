package com.example.springbootjpaoracle.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;

@Getter
@Entity
public class Passport {

  @Id
  @GeneratedValue
  private Long id;

  @Column(nullable = false)
  private String no;

  @OneToOne(fetch = FetchType.LAZY, mappedBy = "passport")
  private Student student;

  protected Passport() {
  }

  public Passport(String no) {
    this.no = no;
  }

  public void setNo(String no) {
    this.no = no;
  }

  public void setStudent(Student student) {
    this.student = student;
  }

  @Override
  public String toString() {
    return String.format("Passport[%s]", no);
  }
}