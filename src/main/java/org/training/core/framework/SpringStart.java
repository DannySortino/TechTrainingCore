package org.training.core.framework;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

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
    method.setAccessible(true);

    return method.invoke(null, argValues);
  }

}
