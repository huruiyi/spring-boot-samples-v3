package com.example.demo6;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

/*
	import org.springframework.stereotype.Component;
	import org.springframework.stereotype.Controller;
	import org.springframework.stereotype.Repository;
	import org.springframework.stereotype.Service;
*/

/*
    相当于 <bean name="userDAO" class="com.example.demo6.UserDAOImpl"></bean>
 */
/* @Component("userDao") */
/* @Controller("userDao") */
/* @Service("userDao") */
@Repository("userDao")
public class UserDaoImpl implements UserDao {

  private String name;

  @Value("小明")
  public void setName(String name) {
    this.name = name;
  }

  @Override
  public void save() {
    System.out.println("UserDaoImpl..." + name);
  }

}
