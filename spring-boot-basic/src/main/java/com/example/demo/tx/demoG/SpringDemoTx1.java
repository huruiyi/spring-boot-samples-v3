package com.example.demo.tx.demoG;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;


@SpringBootTest
@ContextConfiguration("classpath:applicationContextG.tx.xml")
public class SpringDemoTx1 {

	@Resource(name = "accountService")
	private AccountService accountServicea;

	@Test
	public void demo1() {
		accountServicea.transfer("如花", "小宝", 3000d);
	}
}
