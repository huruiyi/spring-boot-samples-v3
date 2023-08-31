package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entities.TSBaseUser;

public interface IBaseUserService extends IService<TSBaseUser> {

  TSBaseUser login(TSBaseUser loginUser);

  TSBaseUser getUser(String id);

}
