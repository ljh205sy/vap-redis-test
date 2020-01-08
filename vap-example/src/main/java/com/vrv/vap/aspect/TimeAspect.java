package com.vrv.vap.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @Author: liujinhui
 * @Date: 2019/9/12 13:59
 */
@Aspect
@Component
public class TimeAspect {
    @Around("execution (* com.vrv.vap.controller.UserController.*(..))")
    public Object TimerAspectHandler(ProceedingJoinPoint procedingJoinPoint) throws Throwable {
        System.out.println("Aspect time aspect...");
        long start = System.currentTimeMillis();
        Object[] args = procedingJoinPoint.getArgs();
        for (Object object : args) {
            System.out.println("------TimeAspect----->" + object.toString());
        }
        // 跟filter中的filter.doFilter();的性质一样，继续往下执行真实调用的方法，返回的object
        Object object = procedingJoinPoint.proceed();
        long end = System.currentTimeMillis();
        System.out.println("Aspect time aspect kill time:" + (end - start));
//        if(object != null ) {
//            throw new RuntimeException("1111");
//        }
        return object;
    }
}
