package org.training.core.framework.typeDefs;

import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder(toBuilder = true)
@EqualsAndHashCode
public class MethodDef {

  private String name;
  private Parameter[] parameters;
  private Annotation[] annotations;
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
