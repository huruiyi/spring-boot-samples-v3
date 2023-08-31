package com.example.demo6;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringDemo6 {

    @Test
    public void demo1() {
        UserDao userDao = new UserDaoImpl();
        userDao.save();
    }

    @Test
    // 普通属性注入
    public void demo2() {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext6.xml");
        UserDao userDao = (UserDao) context.getBean("userDao");
        userDao.save();

        ((ClassPathXmlApplicationContext) context).close();
    }

    @Test
    // 对象属性注入
    public void demo3() {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext6.xml");
        UserService userDao = (UserService) context.getBean("userService");
        userDao.save();

        ((ClassPathXmlApplicationContext) context).close();
    }
}
