package com.example.demo5;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 集合类型的属性注入
 */
public class SpringDemo5 {

	@Test
	public void demo1() {
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext5.xml");

		CollectionBean collectionBean = (CollectionBean) context.getBean("collectionBean");
		System.out.println(collectionBean);

		((ClassPathXmlApplicationContext) context).close();
	}
}
