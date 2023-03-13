package com.example.demo.demoI;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        ApplicationContext context = new GenericXmlApplicationContext("beans-I.xml");

        HelloWorld helloWorld = context.getBean(HelloWorld.class);
        helloWorld.hello();

        List<Holiday> holidays = helloWorld.getHolidays();
        holidays.forEach(m->{
            System.out.println();
            System.out.println(m.getDay());
            System.out.println(m.getMonth());
            System.out.println(m.getGreeting());
        });
    }
}
