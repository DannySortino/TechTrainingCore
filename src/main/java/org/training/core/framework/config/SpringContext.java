package org.training.core.framework.config;

import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.ApplicationContext;

@Slf4j
public class SpringContext {

  private static ApplicationContext context;

  public static <T> T getBean(Class<T> beanClass) {
    if (Objects.isNull(context)) {
      return null;
    }
    try {
      return context.getBean(beanClass);
    } catch (NoUniqueBeanDefinitionException e) {
      log.info("Multiple beans of type {} found", beanClass);
      return null;
    } catch (BeansException e) {
      log.info("Bean {} not found", beanClass);
      return null;
    }
  }

  public static Object getBean(String beanName) {
    if (Objects.isNull(context)) {
      return null;
    }
    try {
      return context.getBean(beanName);
    } catch (BeansException e) {
      log.info("Bean {} not found", beanName);
      return null;
    }
  }

  public static synchronized void setContext(ApplicationContext applicationContext) {
    context = applicationContext;
  }
}
