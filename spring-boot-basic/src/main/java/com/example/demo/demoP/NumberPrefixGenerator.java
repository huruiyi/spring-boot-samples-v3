package com.example.demo.demoP;

import org.springframework.stereotype.Component;

import java.util.Random;


@Component
public class NumberPrefixGenerator implements PrefixGenerator {

    public String getPrefix() {
        Random randomGenerator = new Random();
        int randomInt = randomGenerator.nextInt(100);
        return String.format("%03d", randomInt);
    }
}