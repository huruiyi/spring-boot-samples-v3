package com.example.multidb.service;

import com.example.multidb.annotation.DataSource;
import com.example.multidb.constant.DataSourceType;
import com.example.multidb.entity.User;
import com.example.multidb.repository.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户服务层
 */
@Service
@DataSource(DataSourceType.USER)
public class UserService {

  @Autowired
  private UserRepository userRepository;

  /**
   * 根据ID查询用户
   */
  public User findById(Long id) {
    return userRepository.findById(id);
  }

  /**
   * 查询所有用户
   */
  public List<User> findAll() {
    return userRepository.findAll();
  }

  /**
   * 保存用户，返回插入后的自增ID
   */
  public Long save(User user) {
    return userRepository.save(user);
  }

  /**
   * 更新用户
   */
  public int update(User user) {
    return userRepository.update(user);
  }

  /**
   * 根据ID删除用户
   */
  public int deleteById(Long id) {
    return userRepository.deleteById(id);
  }

  /**
   * 根据用户名查询用户
   */
  public User findByUsername(String username) {
    return userRepository.findByUsername(username);
  }
}
