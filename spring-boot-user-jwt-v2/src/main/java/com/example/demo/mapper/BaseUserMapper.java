package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.model.TSBaseUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BaseUserMapper extends BaseMapper<TSBaseUser> {

}