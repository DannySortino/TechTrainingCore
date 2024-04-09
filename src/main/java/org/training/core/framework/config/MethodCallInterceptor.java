package org.training.core.framework.config;

import net.bytebuddy.asm.Advice;
import org.training.core.framework.utils.MethodInvocationCounterAspect;

public class MethodCallInterceptor {

  @Advice.OnMethodEnter
  public static void onEnter(@Advice.Origin String method) {
    MethodInvocationCounterAspect.incrementMethodInvocationCount(method);
  }

  @Advice.OnMethodExit
  public static void onExit() {
    MethodInvocationCounterAspect.decrementStackCount();
  }

}
