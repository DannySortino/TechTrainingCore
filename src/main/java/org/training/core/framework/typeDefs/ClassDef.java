package org.training.core.framework.typeDefs;

import java.lang.annotation.Annotation;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(toBuilder = true)
public class ClassDef {

  private String name;
  private Annotation[] annotations;
  private Class<?>[] declaredClasses;
  private String packageName;
  private Object[] enumConstants;
  private Class<?>[] interfaces;
  private Class<?> superClass;
  private boolean isPublic;
  private boolean isPrivate;
  private boolean isProtected;
  private boolean isStatic;
  private boolean isFinal;
  private boolean isSynchronized;
  private boolean isVolatile;
  private boolean isTransient;
  private boolean isNative;
  private boolean isInterface;
  private boolean isAbstract;
  private boolean isStrict;
}
