package org.training.core.framework.utils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SpringApplicationRunAspect {

  private static boolean shouldIgnore = false;

  public static void setIgnore(boolean ignore) {
    shouldIgnore = ignore;
  }

  @Around("execution(* org.springframework.boot.SpringApplication.run(..)) && target(springApplication)")
  public Object aroundRun(ProceedingJoinPoint joinPoint, SpringApplication springApplication)
      throws Throwable {
    if (shouldIgnore) {
      return null;
    } else {
      return joinPoint.proceed();
    }
  }
}