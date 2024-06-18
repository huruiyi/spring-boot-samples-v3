package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entities.JwtUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BaseUserMapper extends BaseMapper<JwtUser> {

}
