package org.training.core.framework.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(fluent = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class TypeComparatorConfig {

  private boolean requireExactMatch;
  @Default
  private boolean compareName = true;
  private boolean compareValue;
  private boolean compareAnnotations;
  private boolean compareDeclaredClasses;
  private boolean comparePackageName;
  private boolean compareEnumConstants;
  private boolean compareInterfaces;
  private boolean compareSuperClass;
  private boolean compareParameters;
  private boolean compareIsPrimitive;
  private boolean compareIsPublic;
  private boolean compareIsPrivate;
  private boolean compareIsProtected;
  private boolean compareIsStatic;
  private boolean compareIsFinal;
  private boolean compareIsSynchronized;
  private boolean compareIsVolatile;
  private boolean compareIsTransient;
  private boolean compareIsNative;
  private boolean compareIsInterface;
  private boolean compareIsAbstract;
  private boolean compareIsStrict;

  public void enableAll() {
    compareName = true;
    compareValue = true;
    compareAnnotations = true;
    compareDeclaredClasses = true;
    comparePackageName = true;
    compareEnumConstants = true;
    compareInterfaces = true;
    compareSuperClass = true;
    compareParameters = true;
    compareIsPrimitive = true;
    compareIsPublic = true;
    compareIsPrivate = true;
    compareIsProtected = true;
    compareIsStatic = true;
    compareIsFinal = true;
    compareIsSynchronized = true;
    compareIsVolatile = true;
    compareIsTransient = true;
    compareIsNative = true;
    compareIsInterface = true;
    compareIsAbstract = true;
    compareIsStrict = true;
  }

}