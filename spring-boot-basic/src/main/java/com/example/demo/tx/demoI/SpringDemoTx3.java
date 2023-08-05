package com.example.demo.tx.demoI;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;


@SpringBootTest
@ContextConfiguration("classpath:applicationContextI.tx.xml")
public class SpringDemoTx3 {

	@Resource(name = "accountService")
	private AccountService accountServicea;

	@Test
	public void demo1() {
		accountServicea.transfer("如花", "小宝", 2000d);
	}
}
