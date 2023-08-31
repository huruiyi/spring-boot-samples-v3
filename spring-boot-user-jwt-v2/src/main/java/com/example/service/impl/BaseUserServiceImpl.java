package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mapper.BaseUserMapper;
import com.example.model.TSBaseUser;
import com.example.service.IBaseUserService;
import org.springframework.stereotype.Service;

@Service
public class BaseUserServiceImpl extends ServiceImpl<BaseUserMapper, TSBaseUser> implements IBaseUserService {

  @Override
  public TSBaseUser login(TSBaseUser loginUser) {

    LambdaQueryWrapper<TSBaseUser> wrapper = new LambdaQueryWrapper<>();
    wrapper.eq(TSBaseUser::getUserName , loginUser.getUserName())
        .eq(TSBaseUser::getPassword , loginUser.getPassword());

    TSBaseUser baseUser = baseMapper.selectOne(wrapper);

    if (ObjectUtils.isNotEmpty(baseUser)){
      return baseUser;
    }

    return null;
  }

  @Override
  public TSBaseUser getUser(String id) {
    return baseMapper.selectById(id);
  }
}
