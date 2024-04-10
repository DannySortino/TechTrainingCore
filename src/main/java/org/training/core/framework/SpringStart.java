package org.training.core.framework;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.training.core.framework.config.AutoConfig;
import org.training.core.framework.utils.MethodInvocationCounterAspect;
import org.training.core.framework.utils.SpringApplicationRunAspect;

@Slf4j
public class SpringStart {

  public static Object start(String className) {
    return start(className, new Pair<>(String[].class, new String[]{}));
  }

  @SafeVarargs
  @SneakyThrows
  public static Object start(String className, Pair<Class<?>, Object>... args) {
    Class<?>[] argTypes = Objects.isNull(args) ? null
        : Arrays.stream(args).map(Pair::getLeft).toArray(Class<?>[]::new);
    Object[] argValues = Objects.isNull(args) ? null
        : Arrays.stream(args).map(Pair::getRight).toArray(Object[]::new);
    String[] stringArgs = Objects.isNull(args) ? null
        : Arrays.stream(args).map(arg -> arg.getRight().toString()).toArray(String[]::new);

    Class<?> realClass;
    Method method;

    try {
      realClass = Class.forName(className);
      method = realClass.getDeclaredMethod("main", argTypes);
    } catch (ClassNotFoundException e) {
      log.error("You are likely missing the expected class - {}", className, e);
      throw new RuntimeException(e);
    } catch (NoSuchMethodException e) {
      log.error("You are likely missing the expected method - main", e);
      throw new RuntimeException(e);
    }

    SpringApplication.run(new Class[]{realClass, AutoConfig.class}, stringArgs);

    SpringApplicationRunAspect.setIgnore(true);
    Object result = method.invoke(null, argValues);
    SpringApplicationRunAspect.setIgnore(false);

    return result;

  }

}
