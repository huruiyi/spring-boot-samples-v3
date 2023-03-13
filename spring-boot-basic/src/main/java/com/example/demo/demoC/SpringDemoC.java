package com.example.demo.demoC;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;



@SpringBootTest
@ContextConfiguration("classpath:applicationContextC.xml")
public class SpringDemoC {

    @Resource(name = "orderDao")
    private OrderDao orderDao;

    @Test
    public void demo1() {
        System.out.println("*******************************************************************");
        orderDao.save();
        System.out.println("*******************************************************************");
        orderDao.delete();
        System.out.println("*******************************************************************");
        orderDao.update();
        System.out.println("*******************************************************************");
        orderDao.find();
        System.out.println("*******************************************************************");
    }
}
