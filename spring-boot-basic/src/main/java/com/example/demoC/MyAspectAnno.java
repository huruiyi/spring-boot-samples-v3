package com.example.demoC;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class MyAspectAnno {

  @Before(value = "execution(* com.example.demoC.OrderDao.save(..))")
  public void before() {
    System.out.println("前置增强......");
  }

  @AfterReturning(value = "execution(* com.example.demoC.OrderDao.delete(..))", returning = "result")
  public void afterReturning(Object result) {
    System.out.println("后置增强......" + result);
  }

  @Around(value = "execution(* com.example.demoC.OrderDao.update(..))")
  public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
    System.out.println("before update......");
    Object proceed = joinPoint.proceed();
    System.out.println("after  update......");
    return proceed;
  }

  @AfterThrowing(value = "com.example.demoC.MyAspectAnno.pointCut1()", throwing = "errorMessage")
  public void afterThrowing(Throwable errorMessage) {
    System.out.println("异常抛出......" + errorMessage.getMessage());
  }

  @After(value = "com.example.demoC.MyAspectAnno.pointCut1()")
  public void after() {
    System.out.println("最终增强......");
  }

  /* AOP切入点配置 */
  @Pointcut(value = "execution(* com.example.demoC.OrderDao.find(..))")
  public void pointCut1() {
  }

  @Pointcut(value = "execution(* com.example.demoC.OrderDao.save(..))")
  public void pointCut2() {
  }

  @Pointcut(value = "execution(* com.example.demoC.OrderDao.update(..))")
  public void pointCut3() {
  }

  @Pointcut(value = "execution(* com.example.demoC.OrderDao.delete(..))")
  public void pointCut4() {
  }
}
