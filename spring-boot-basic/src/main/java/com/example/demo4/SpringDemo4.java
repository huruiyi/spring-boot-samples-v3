package com.example.demo4;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 属性注入的方式：
 */
public class SpringDemo4 {

    @Test
    public void demo1() {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext4.xml");
        Car1 car = (Car1) context.getBean("car");
        System.out.println(car);

        ((ClassPathXmlApplicationContext) context).close();
    }

    @Test
    public void demo2() {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext4.xml");
        Car2 car2_1 = (Car2) context.getBean("car2_1");
        System.out.println(car2_1);

        Car2 car2_2 = (Car2) context.getBean("car2_2");
        System.out.println(car2_2);

        Car2 car2_3 = (Car2) context.getBean("car2_3");
        System.out.println(car2_3);

        ((ClassPathXmlApplicationContext) context).close();
    }

    @Test
    public void demo3() {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext4.xml");
        Employee employee_1 = (Employee) context.getBean("employee_1");
        System.out.println(employee_1);

        Employee employee_2 = (Employee) context.getBean("employee_2");
        System.out.println(employee_2);

        Employee employee_3 = (Employee) context.getBean("employee_3");
        System.out.println(employee_3);

        ((ClassPathXmlApplicationContext) context).close();
    }
}
