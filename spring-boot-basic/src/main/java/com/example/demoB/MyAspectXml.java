package com.example.demoB;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

public class MyAspectXml {

  public void checkPri(JoinPoint joinpoint) {
    System.out.println("=================权限校验=================" + joinpoint);
  }

  /* result 对应配置文件中的returning值 */
  public void writeLog(JoinPoint joinpoint, Object result) {
    System.out.println("=================日志记录=================" + joinpoint);
    System.out.println("返回值:" + result);
  }

  public Object arround(ProceedingJoinPoint joinPoint) throws Throwable {
    System.out.println("=================执行前=================" + joinPoint.getSignature());
    Object object = joinPoint.proceed();
    System.out.println("=================执行后=================" + joinPoint.getSignature());
    return object;
  }

  public void afterThrowing(Throwable errorMessage) {
    System.out.println("异常抛出通知......" + errorMessage);
  }

  public void after() {
    System.out.println("最终通知......");
  }
}
