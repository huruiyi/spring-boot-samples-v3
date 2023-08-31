package com.example.demoA;

import java.lang.reflect.Method;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

public class CglibProxy implements MethodInterceptor {
    private CustomerDao customerDao;

    public CglibProxy(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    /**
     * Cglib产生代理的方法
     *
     * @return
     */
    public CustomerDao createProxy() {
        Enhancer enhancer = new Enhancer();

        /* 设置父类 */
        enhancer.setSuperclass(customerDao.getClass());

        // 设置回调:(类似于InvocationHandler)
        enhancer.setCallback(this);

        CustomerDao proxy = (CustomerDao) enhancer.create();
        return proxy;
    }

    @Override
    public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {

        if ("save".equals(method.getName())) {
            // 方法的增强
            System.out.println("权限校验......");
            return methodProxy.invokeSuper(proxy, args);
        }
        return methodProxy.invokeSuper(proxy, args);
    }

}
