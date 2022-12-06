package com.nowcoder.community.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

//@Component
//@Aspect //方面组件
public class AlphaAspect {

    // 定义切点 pointcut
    // 第一个*表示方法的返回值，即什么返回值都行
    // com.nowcoder.community.service.*.* 表示service下的所有的业务组件下的所有方法
    // (..)表示所有的参数
    @Pointcut("execution(* com.nowcoder.community.service.*.*(..))")
    public void pointcut() {

    }

    // 定义通知advice
    @Before("pointcut()") //在连接点的开头
    public void before() {
        System.out.println("before");
    }

    @After("pointcut()") // 连接点之后
    public void after() {
        System.out.println("after");
    }

    @AfterReturning("pointcut()") //在有了返回值以后
    public void afterReturning() {
        System.out.println("afterReturning");
    }

    @AfterThrowing("pointcut()") //在抛异常时
    public void afterThrowing() {
        System.out.println("afterThrowing");
    }

    @Around("pointcut()") // 前后都织入逻辑，参数为连接点
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("around before");// 这就是在连接点之前处理的逻辑
        Object obj = joinPoint.proceed();// 调用目标组件的方法
        System.out.println("around after");// 这就是在连接点之后处理的逻辑
        return obj;
    }
}
