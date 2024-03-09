package org.training.core.framework.utils;

import java.util.AbstractQueue;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.training.core.framework.Pair;

@Aspect
@Component
@Slf4j
public class MethodInvocationCounterAspect {

  @Getter
  private static final ConcurrentMap<String, Integer> methodInvocationCount = new ConcurrentHashMap<>();
  @Getter
  private static final Queue<Pair<String, Integer>> methodInvocationStack = new ConcurrentLinkedQueue<>();
  private static final AtomicInteger methodDepthCounter = new AtomicInteger(0);


  //@Pointcut("!within(java..*) && !within(org.training.core.framework..*) && !within(org.aspectj..*)")
  @Pointcut("execution(* org.training..*(..))")
  public void targetMethods() {
  }

  @Pointcut("execution(public * java..*.*(..)) || execution(protected * java..*.*(..)) || execution(* java..*.*(..))")
  public void targetJavaPublicMethods() {
  }

  @Around("targetMethods() || targetJavaPublicMethods()")
  public Object countMethodInvocation(ProceedingJoinPoint joinPoint) throws Throwable {
    String methodName =
        joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
    log.info("Method invoked: {}", methodName);
    methodInvocationCount.merge(methodName, 1, Integer::sum);

    return joinPoint.proceed();
  }

  public static void incrementMethodInvocationCount(String methodName) {
    methodInvocationCount.merge(methodName, 1, Integer::sum);
    methodInvocationStack.add(new Pair<>(methodName, methodDepthCounter.incrementAndGet()));
  }

  public static void decrementStackCount() {
    methodDepthCounter.decrementAndGet();
  }

}

