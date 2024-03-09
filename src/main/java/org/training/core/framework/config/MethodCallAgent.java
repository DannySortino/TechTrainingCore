package org.training.core.framework.config;

import java.lang.instrument.Instrumentation;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.matcher.ElementMatchers;

public class MethodCallAgent {

  public static void premain(String agentArgs, Instrumentation inst) {
    new AgentBuilder.Default()
        .type(ElementMatchers.any())
        .transform(
            (builder, typeDescription, classLoader, javaModule, typePool) ->
                builder.visit(
                    Advice.to(MethodCallInterceptor.class)
                        .on(ElementMatchers.isPublic()
                            .and(ElementMatchers.not(ElementMatchers.isConstructor()))
                            .and(ElementMatchers.not(ElementMatchers.nameStartsWith("aspectOf"))))))
        .installOn(inst);
  }

}

