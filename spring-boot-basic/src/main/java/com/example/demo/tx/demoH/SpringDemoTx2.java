package com.example.demo.tx.demoH;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@SpringBootTest
@ContextConfiguration("classpath:applicationContextH.tx.xml")
public class SpringDemoTx2 {

	@Resource(name = "accountService")
	private AccountService accountServicea;

	@Test
	public void demo1() {
		accountServicea.transfer("如花", "小宝", 1000d);
	}
}
