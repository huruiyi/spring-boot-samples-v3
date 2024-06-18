package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entities.JwtUser;

public interface JwtUserService extends IService<JwtUser> {

  JwtUser login(String userName,String password);

  JwtUser getUser(String id);

}
