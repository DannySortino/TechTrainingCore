package org.training.core.framework.typeDefs;

import java.lang.annotation.Annotation;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.training.core.framework.ClassDescriptor;

@Getter
@Builder(toBuilder = true)
@EqualsAndHashCode
public class FieldDef {

  private String name;
  private Object value;
  private Annotation[] annotations;
  private ClassDescriptor classDescriptor;
  private boolean isPrimitive;
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
