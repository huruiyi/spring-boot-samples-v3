package com.example.demo8;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringDemo8 {

	@Test
	public void demo1() {
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext8.xml");
		ProductService productService = (ProductService) context.getBean("productService");
		productService.save();

		((ClassPathXmlApplicationContext) context).close();
	}

	@Test
	public void demo2() {
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext8.xml");
		OrderService service = (OrderService) context.getBean("orderService");
		service.save();

		((ClassPathXmlApplicationContext) context).close();
	}
}
