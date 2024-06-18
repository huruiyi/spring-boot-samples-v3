package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mapper.BaseUserMapper;
import com.example.entities.JwtUser;
import com.example.service.JwtUserService;
import org.springframework.stereotype.Service;

@Service
public class JwtUserServiceImpl extends ServiceImpl<BaseUserMapper, JwtUser> implements JwtUserService {

  @Override
  public JwtUser login(String userName, String password) {
    LambdaQueryWrapper<JwtUser> wrapper = new LambdaQueryWrapper<>();
    wrapper.eq(JwtUser::getUserName, userName)
        .eq(JwtUser::getPassword, password);
    JwtUser baseUser = baseMapper.selectOne(wrapper);
    if (ObjectUtils.isNotEmpty(baseUser)) {
      return baseUser;
    }
    return null;
  }

  @Override
  public JwtUser getUser(String id) {
    return baseMapper.selectById(id);
  }

}
