package com.example.demo7;

import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringDemo7 {

    @Test
    public void demo1() {

        // ApplicationContext acontext = new ClassPathXmlApplicationContext("applicationContext7.xml");

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext7.xml");
        CustomerService service1 = (CustomerService) context.getBean("customerService");
        service1.save();

        CustomerService service2 = (CustomerService) context.getBean("customerService");
        service2.save();

        System.out.println(service1);
        System.out.println(service2);
        context.close();
        //context.destroy();
    }
}
