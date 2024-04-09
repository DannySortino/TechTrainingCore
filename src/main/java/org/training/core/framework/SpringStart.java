package org.training.core.framework;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.training.core.framework.config.AutoConfig;

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
    String[] argValues = Objects.isNull(args) ? null
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

    return SpringApplication.run(new Class[]{realClass, AutoConfig.class}, argValues);
  }

}
