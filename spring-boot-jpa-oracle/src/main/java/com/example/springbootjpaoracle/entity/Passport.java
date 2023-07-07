package com.example.springbootjpaoracle.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Passport {

    @Id
    private Long id;

    @Column(nullable = false)
    private String no;

    protected Passport() {
    }

    public Passport(String number) {
        this.no = number;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return String.format("Passport[%s]", no);
    }
}