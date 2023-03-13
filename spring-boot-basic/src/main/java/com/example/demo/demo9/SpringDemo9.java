package com.example.demo.demo9;

import org.junit.jupiter.api.Test;

public class SpringDemo9 {

	@Test
	public void demo1() {
		UserDao userDao = new UserDaoImpl();
		UserDao proxy=new JdkProxy(userDao).CreateProxy();
		proxy.save();
		proxy.delete();
		proxy.update();
		proxy.find();
	}
}
