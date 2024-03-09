package org.training.core.framework.config;

import static org.training.core.framework.Constants.UNIQUE_CUSTOM_METHOD_NAME;

import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.training.core.framework.utils.ClassOverrideCreator;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;

//@Component
@Slf4j
public class SpringFactoryConfig implements BeanDefinitionRegistryPostProcessor {

  private static final Map<String, BeanDefinition> constructedRootBeanDefinitions = new HashMap<>();

  @Override
  public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry)
      throws BeansException {
    String[] beanDefinitionNames = registry.getBeanDefinitionNames();
    for (String beanDefinitionName : beanDefinitionNames) {
      BeanDefinition beanDefinition = registry.getBeanDefinition(beanDefinitionName);
      String beanClassName = beanDefinition.getBeanClassName();

      if (beanClassName == null) {
        log.warn("Bean definition {} has no class name", beanDefinitionName);
        continue;
      }

      handleBeanDefinition(registry, beanDefinitionName, beanClassName, beanDefinition);
    }
  }

  private void handleBeanDefinition(BeanDefinitionRegistry registry, String beanDefinitionName,
      String beanClassName, BeanDefinition beanDefinition) {

    // Handle already constructed classes
    if (constructedRootBeanDefinitions.containsKey(beanClassName)) {
      registry.removeBeanDefinition(beanDefinitionName);
      registry.registerBeanDefinition(beanDefinitionName,
          constructedRootBeanDefinitions.get(beanClassName));
      return;
    }

    // Handle custom methods
    if (beanClassName.contains(UNIQUE_CUSTOM_METHOD_NAME)
        && constructedRootBeanDefinitions.containsKey(
        beanClassName.replace(UNIQUE_CUSTOM_METHOD_NAME, ""))) {
      registry.removeBeanDefinition(beanDefinitionName);
      registry.registerBeanDefinition(beanDefinitionName,
          constructedRootBeanDefinitions.get(beanClassName.replace(UNIQUE_CUSTOM_METHOD_NAME, "")));
      return;
    }

    // Handle already overridden classes where bean class name is original class name but already registered
    // with the overriden name
    if (constructedRootBeanDefinitions.containsKey(beanClassName + UNIQUE_CUSTOM_METHOD_NAME)) {
      registry.removeBeanDefinition(beanDefinitionName);
      registry.registerBeanDefinition(beanDefinitionName,
          constructedRootBeanDefinitions.get(beanClassName + UNIQUE_CUSTOM_METHOD_NAME));
      return;
    }

    // Handle already overridden classes where bean class name is original class name
    if (beanDefinitionName.contains(UNIQUE_CUSTOM_METHOD_NAME)) {
      registry.removeBeanDefinition(beanDefinitionName);
      registry.registerBeanDefinition(beanDefinitionName, beanDefinition);
      constructedRootBeanDefinitions.put(beanClassName, beanDefinition);
      return;
    }

    //TODO: Danny unable to do this for all spring beans - ideally would work
    //TODO: Need to figure what to do for final classes
    //Need to skip my springContext from being overridden else struggle to access the context correctly
    if (beanDefinitionName.contains("org.springframework") || beanDefinitionName.equals(
        "springContext")) {
      log.warn("Skipping override for bean definition {} with class name {}", beanDefinitionName,
          beanClassName);
      return;
    }

    // Handle other cases
    processBeanDefinition(registry, beanDefinitionName, beanClassName);
  }

  private void processBeanDefinition(BeanDefinitionRegistry registry, String beanDefinitionName,
      String beanClassName) {
    Class<?> realClass;
    try {
      realClass = Class.forName(beanClassName);
    } catch (ClassNotFoundException e) {
      log.error("You are likely missing the expected bean class - {}", beanClassName, e);
      throw new RuntimeException(e);
    }
    Class<?> beanClass = ClassOverrideCreator.createCustomClass(beanClassName, realClass);
    RootBeanDefinition newBean = new RootBeanDefinition(beanClass);

    registry.removeBeanDefinition(beanDefinitionName);
    registry.registerBeanDefinition(beanDefinitionName, newBean);
    constructedRootBeanDefinitions.put(beanClassName, newBean);
  }


  @Override
  public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory)
      throws BeansException {
  }
}