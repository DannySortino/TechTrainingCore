package org.training.core.framework.config;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AutoConfig {

  @Autowired
  ApplicationContext context;

  @PostConstruct
  public void init() {
    SpringContext.setContext(context);
  }

}
