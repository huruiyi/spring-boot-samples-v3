package com.example.demo.controller;


import com.example.demo.annotation.LoginToken;
import com.example.demo.annotation.PassToken;
import com.example.demo.model.R;
import com.example.demo.model.ResultCode;
import com.example.demo.model.TSBaseUser;
import com.example.demo.service.IBaseUserService;
import com.example.demo.service.TokenService;
import com.example.demo.vo.TSBaseUserVo;
import jakarta.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

  @Resource
  private IBaseUserService baseUserService;

  @Resource
  private TokenService tokenService;


  @PassToken
  @RequestMapping(value = "/login" , method = {RequestMethod.GET,RequestMethod.POST})
  public R<?> login(TSBaseUserVo userVo){
    // 获取登陆用户信息
    TSBaseUser tsBaseUser=new TSBaseUser();
    tsBaseUser.setId(userVo.getId());
    tsBaseUser.setUserName(userVo.getUserName());
    tsBaseUser.setPassword(userVo.getPassword());

    TSBaseUser user = baseUserService.login(tsBaseUser);
    // 从jwt存储的用户信息获取 BaseUserInfo.getUserName()
    if (ObjectUtils.isEmpty(user)){
      return R.failure(ResultCode.of("用户名或密码错误"));
    }else {
      Map<String,Object> data = new HashMap<>();
      data.put("user",user);
      data.put("token",tokenService.getToken(userVo));
      return R.success(data);
    }
  }


  @LoginToken
  @RequestMapping("message")
  public R<String> message(){
    return R.success("Ok");
  }

}
