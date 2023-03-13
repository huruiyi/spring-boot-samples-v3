package com.example.demo.demo4;

public class CarInfo {
    @SuppressWarnings("unused")
    private String name;

    public String getName() {
        return "摩托车";
    }

    public Double calculatorPrice() {
        return Math.random() * 3000;
    }
}
