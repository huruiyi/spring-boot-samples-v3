package com.example.demo6;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 对象的注入 @Autowired
 */
@Service("userService")
public class UserServiceImpl implements UserService {

  /*
   * @Autowired
   *
   * @Qualifier("userDao")
   */
  /* @Autowired 加 @Qualifier("userDao") 同时使用 指向UserDao的save方法 (按名字注入) */

  @Autowired
  /* @Autowired 指向自己的save方法(按照属性注入) */

  private UserDao userDao;

  @Override
  public void save() {
    System.out.println("UserServiceImpl");
    userDao.save();
  }
}
