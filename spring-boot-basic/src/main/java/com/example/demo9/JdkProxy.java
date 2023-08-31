package com.example.demo9;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JdkProxy implements InvocationHandler {

    private UserDao userDao;

    public JdkProxy(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * 产生UserDao的代理方法
     *
     * @return
     */
    public UserDao CreateProxy() {
        UserDao userDaoProxy = (UserDao) Proxy.newProxyInstance(
                userDao.getClass().getClassLoader(),
                userDao.getClass().getInterfaces(),
                this);
        return userDaoProxy;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if ("save".equals(method.getName()) || "update".equals(method.getName())) {
            // 方法的增强
            System.out.println("权限校验......");
            return method.invoke(userDao, args);
        }
        return method.invoke(userDao, args);
    }

}
