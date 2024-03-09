package org.training.core.framework;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.training.core.framework.config.TypeComparatorConfig;
import org.training.core.framework.typeDefs.ClassDef;
import org.training.core.framework.typeDefs.FieldDef;
import org.training.core.framework.typeDefs.MethodDef;

@RequiredArgsConstructor
@Slf4j
public class TypeComparator {

  private final TypeComparatorConfig typeComparatorConfig;

  @SuppressWarnings("unchecked")
  public boolean equals(@NonNull Map<String, ?> expectedParams,
      @NonNull Map<String, ?> actualParams) {

    if (expectedParams.isEmpty() && actualParams.isEmpty()) {
      return true;
    } else if (expectedParams.isEmpty() || actualParams.isEmpty()) {
      log.warn(
          "One of the input maps is empty whilst the other is not - expectedParams: {}, actualParams: {}",
          expectedParams, actualParams);
      return false;
    }

    Class<?> param1Class = expectedParams.values().iterator().next().getClass();
    Class<?> param2Class = actualParams.values().iterator().next().getClass();

    if (!param1Class.equals(param2Class)) {
      log.warn("INVALID type equality check found. expectedParams - {}, actualParams - {}",
          expectedParams, actualParams);
      return false;
    }

    if (param1Class.equals(FieldDef.class)) {
      return equalFields((Map<String, FieldDef>) expectedParams,
          (Map<String, FieldDef>) actualParams);
    } else if (param1Class.equals(MethodDef.class)) {
      return equalMethods((Map<String, MethodDef>) expectedParams,
          (Map<String, MethodDef>) actualParams);
    } else if (param1Class.equals(ClassDef.class)) {
      return equalClasses((Map<String, ClassDef>) expectedParams,
          (Map<String, ClassDef>) actualParams);
    }

    throw new RuntimeException(
        String.format("Unknown compare type expectedParams - %s, actualParams - %s", param1Class,
            param2Class));
  }

  private boolean equalFields(Map<String, FieldDef> expectedFields,
      Map<String, FieldDef> actualFields) {
    if (typeComparatorConfig.requireExactMatch() && !expectedFields.keySet()
        .equals(actualFields.keySet())) {
      log.warn("Incorrect quantities for keys on expectedFields - {}, and actualFields - {}",
          expectedFields.keySet(), actualFields.keySet());
      return false;
    }

    boolean areEquals = true;

    for (Map.Entry<String, FieldDef> entry : expectedFields.entrySet()) {
      String fieldName = entry.getKey();
      FieldDef expectedField = expectedFields.get(fieldName);
      FieldDef actualField = actualFields.get(fieldName);

      if (typeComparatorConfig.compareName() && !Objects.equals(expectedField.getName(),
          actualField.getName())) {
        log.warn("Difference detected for NAME field - expectedField: {}, actualField: {}",
            expectedField.getName(), actualField.getName());
        areEquals = false;
      }

      if (typeComparatorConfig.compareValue() && !Objects.equals(expectedField.getValue(),
          actualField.getValue())) {
        log.warn("Difference detected for VALUE field - expectedField: {}, actualField: {}",
            expectedField.getValue(), actualField.getValue());
        areEquals = false;
      }

      if (typeComparatorConfig.compareAnnotations() && !Arrays.equals(
          expectedField.getAnnotations(), actualField.getAnnotations())) {
        log.warn("Difference detected for ANNOTATIONS field - expectedField: {}, actualField: {}",
            expectedField.getAnnotations(), actualField.getAnnotations());
        areEquals = false;
      }

      if (typeComparatorConfig.compareIsPrimitive()
          && expectedField.isPrimitive() != actualField.isPrimitive()) {
        log.warn("Difference detected for IS_PRIMITIVE field - expectedField: {}, actualField: {}",
            expectedField.isPrimitive(), actualField.isPrimitive());
        areEquals = false;
      }

      if (typeComparatorConfig.compareIsPublic()
          && expectedField.isPublic() != actualField.isPublic()) {
        log.warn("Difference detected for IS_PUBLIC field - expectedField: {}, actualField: {}",
            expectedField.isPublic(), actualField.isPublic());
        areEquals = false;
      }

      if (typeComparatorConfig.compareIsPrivate()
          && expectedField.isPrivate() != actualField.isPrivate()) {
        log.warn("Difference detected for IS_PRIVATE field - expectedField: {}, actualField: {}",
            expectedField.isPrivate(), actualField.isPrivate());
        areEquals = false;
      }

      if (typeComparatorConfig.compareIsProtected()
          && expectedField.isProtected() != actualField.isProtected()) {
        log.warn("Difference detected for IS_PROTECTED field - expectedField: {}, actualField: {}",
            expectedField.isProtected(), actualField.isProtected());
        areEquals = false;
      }

      if (typeComparatorConfig.compareIsStatic()
          && expectedField.isStatic() != actualField.isStatic()) {
        log.warn("Difference detected for IS_STATIC field - expectedField: {}, actualField: {}",
            expectedField.isStatic(), actualField.isStatic());
        areEquals = false;
      }

      if (typeComparatorConfig.compareIsFinal()
          && expectedField.isFinal() != actualField.isFinal()) {
        log.warn("Difference detected for IS_FINAL field - expectedField: {}, actualField: {}",
            expectedField.isFinal(), actualField.isFinal());
        areEquals = false;
      }

      if (typeComparatorConfig.compareIsSynchronized()
          && expectedField.isSynchronized() != actualField.isSynchronized()) {
        log.warn(
            "Difference detected for IS_SYNCHRONIZED field - expectedField: {}, actualField: {}",
            expectedField.isSynchronized(), actualField.isSynchronized());
        areEquals = false;
      }

      if (typeComparatorConfig.compareIsVolatile()
          && expectedField.isVolatile() != actualField.isVolatile()) {
        log.warn("Difference detected for IS_VOLATILE field - expectedField: {}, actualField: {}",
            expectedField.isVolatile(), actualField.isVolatile());
        areEquals = false;
      }

      if (typeComparatorConfig.compareIsTransient()
          && expectedField.isTransient() != actualField.isTransient()) {
        log.warn("Difference detected for IS_TRANSIENT field - expectedField: {}, actualField: {}",
            expectedField.isTransient(), actualField.isTransient());
        areEquals = false;
      }

      if (typeComparatorConfig.compareIsNative()
          && expectedField.isNative() != actualField.isNative()) {
        log.warn("Difference detected for IS_NATIVE field - expectedField: {}, actualField: {}",
            expectedField.isNative(), actualField.isNative());
        areEquals = false;
      }

      if (typeComparatorConfig.compareIsInterface()
          && expectedField.isInterface() != actualField.isInterface()) {
        log.warn("Difference detected for IS_INTERFACE field - expectedField: {}, actualField: {}",
            expectedField.isInterface(), actualField.isInterface());
        areEquals = false;
      }

      if (typeComparatorConfig.compareIsAbstract()
          && expectedField.isAbstract() != actualField.isAbstract()) {
        log.warn("Difference detected for IS_ABSTRACT field - expectedField: {}, actualField: {}",
            expectedField.isAbstract(), actualField.isAbstract());
        areEquals = false;
      }

      if (typeComparatorConfig.compareIsStrict()
          && expectedField.isStrict() != actualField.isStrict()) {
        log.warn("Difference detected for IS_STRICT field - expectedField: {}, actualField: {}",
            expectedField.isStrict(), actualField.isStrict());
        areEquals = false;
      }

    }

    return areEquals;
  }

  private boolean equalMethods(Map<String, MethodDef> expectedMethods,
      Map<String, MethodDef> actualMethods) {
    if (typeComparatorConfig.requireExactMatch() && !expectedMethods.keySet()
        .equals(actualMethods.keySet())) {
      log.warn("Incorrect quantities for keys on expectedMethods - {}, and actualMethods - {}",
          expectedMethods.keySet(), actualMethods.keySet());
      return false;
    }

    boolean areEquals = true;

    for (Map.Entry<String, MethodDef> entry : expectedMethods.entrySet()) {
      String methodName = entry.getKey();
      MethodDef expectedMethod = expectedMethods.get(methodName);
      MethodDef actualMethod = actualMethods.get(methodName);

      if (typeComparatorConfig.compareName() && !Objects.equals(expectedMethod.getName(),
          actualMethod.getName())) {
        log.warn("Difference detected for NAME field - expectedMethod: {}, actualMethod: {}",
            expectedMethod.getName(), actualMethod.getName());
        areEquals = false;
      }

      if (typeComparatorConfig.compareParameters() && !Arrays.equals(expectedMethod.getParameters(),
          actualMethod.getParameters())) {
        log.warn("Difference detected for PARAMETERS field - expectedMethod: {}, actualMethod: {}",
            expectedMethod.getParameters(), actualMethod.getParameters());
        areEquals = false;
      }

      if (typeComparatorConfig.compareAnnotations() && !Arrays.equals(
          expectedMethod.getAnnotations(), actualMethod.getAnnotations())) {
        log.warn("Difference detected for ANNOTATIONS field - expectedMethod: {}, actualMethod: {}",
            expectedMethod.getAnnotations(), actualMethod.getAnnotations());
        areEquals = false;
      }

      if (typeComparatorConfig.compareIsPublic()
          && expectedMethod.isPublic() != actualMethod.isPublic()) {
        log.warn("Difference detected for IS_PUBLIC field - expectedMethod: {}, actualMethod: {}",
            expectedMethod.isPublic(), actualMethod.isPublic());
        areEquals = false;
      }

      if (typeComparatorConfig.compareIsPrivate()
          && expectedMethod.isPrivate() != actualMethod.isPrivate()) {
        log.warn("Difference detected for IS_PRIVATE field - expectedMethod: {}, actualMethod: {}",
            expectedMethod.isPrivate(), actualMethod.isPrivate());
        areEquals = false;
      }

      if (typeComparatorConfig.compareIsProtected()
          && expectedMethod.isProtected() != actualMethod.isProtected()) {
        log.warn(
            "Difference detected for IS_PROTECTED field - expectedMethod: {}, actualMethod: {}",
            expectedMethod.isProtected(), actualMethod.isProtected());
        areEquals = false;
      }

      if (typeComparatorConfig.compareIsStatic()
          && expectedMethod.isStatic() != actualMethod.isStatic()) {
        log.warn("Difference detected for IS_STATIC field - expectedMethod: {}, actualMethod: {}",
            expectedMethod.isStatic(), actualMethod.isStatic());
        areEquals = false;
      }

      if (typeComparatorConfig.compareIsFinal()
          && expectedMethod.isFinal() != actualMethod.isFinal()) {
        log.warn("Difference detected for IS_FINAL field - expectedMethod: {}, actualMethod: {}",
            expectedMethod.isFinal(), actualMethod.isFinal());
        areEquals = false;
      }

      if (typeComparatorConfig.compareIsSynchronized()
          && expectedMethod.isSynchronized() != actualMethod.isSynchronized()) {
        log.warn(
            "Difference detected for IS_SYNCHRONIZED field - expectedMethod: {}, actualMethod: {}",
            expectedMethod.isSynchronized(), actualMethod.isSynchronized());
        areEquals = false;
      }

      if (typeComparatorConfig.compareIsVolatile()
          && expectedMethod.isVolatile() != actualMethod.isVolatile()) {
        log.warn("Difference detected for IS_VOLATILE field - expectedMethod: {}, actualMethod: {}",
            expectedMethod.isVolatile(), actualMethod.isVolatile());
        areEquals = false;
      }

      if (typeComparatorConfig.compareIsTransient()
          && expectedMethod.isTransient() != actualMethod.isTransient()) {
        log.warn(
            "Difference detected for IS_TRANSIENT field - expectedMethod: {}, actualMethod: {}",
            expectedMethod.isTransient(), actualMethod.isTransient());
        areEquals = false;
      }

      if (typeComparatorConfig.compareIsNative()
          && expectedMethod.isNative() != actualMethod.isNative()) {
        log.warn("Difference detected for IS_NATIVE field - expectedMethod: {}, actualMethod: {}",
            expectedMethod.isNative(), actualMethod.isNative());
        areEquals = false;
      }

      if (typeComparatorConfig.compareIsInterface()
          && expectedMethod.isInterface() != actualMethod.isInterface()) {
        log.warn(
            "Difference detected for IS_INTERFACE field - expectedMethod: {}, actualMethod: {}",
            expectedMethod.isInterface(), actualMethod.isInterface());
        areEquals = false;
      }

      if (typeComparatorConfig.compareIsAbstract()
          && expectedMethod.isAbstract() != actualMethod.isAbstract()) {
        log.warn("Difference detected for IS_ABSTRACT field - expectedMethod: {}, actualMethod: {}",
            expectedMethod.isAbstract(), actualMethod.isAbstract());
        areEquals = false;
      }

      if (typeComparatorConfig.compareIsStrict()
          && expectedMethod.isStrict() != actualMethod.isStrict()) {
        log.warn("Difference detected for IS_STRICT field - expectedMethod: {}, actualMethod: {}",
            expectedMethod.isStrict(), actualMethod.isStrict());
        areEquals = false;
      }

    }

    return areEquals;
  }

  private boolean equalClasses(Map<String, ClassDef> expectedClasses,
      Map<String, ClassDef> actualClasses) {
    if (typeComparatorConfig.requireExactMatch() && !expectedClasses.keySet()
        .equals(actualClasses.keySet())) {
      log.warn("Incorrect quantities for keys on expectedClasses - {}, and actualClasses - {}",
          expectedClasses.keySet(), actualClasses.keySet());
      return false;
    }

    boolean areEquals = true;

    for (Map.Entry<String, ClassDef> entry : expectedClasses.entrySet()) {
      String className = entry.getKey();
      ClassDef expectedClass = expectedClasses.get(className);
      ClassDef actualClass = actualClasses.get(className);

      if (typeComparatorConfig.compareName() && !Objects.equals(expectedClass.getName(),
          actualClass.getName())) {
        log.warn("Difference detected for NAME field - expectedClass: {}, actualClass: {}",
            expectedClass.getName(), actualClass.getName());
        areEquals = false;
      }

      if (typeComparatorConfig.compareAnnotations() && !Arrays.equals(
          expectedClass.getAnnotations(), actualClass.getAnnotations())) {
        log.warn("Difference detected for ANNOTATIONS field - expectedClass: {}, actualClass: {}",
            expectedClass.getAnnotations(), actualClass.getAnnotations());
        areEquals = false;
      }

      if (typeComparatorConfig.compareDeclaredClasses() && !Arrays.equals(
          expectedClass.getDeclaredClasses(), actualClass.getDeclaredClasses())) {
        log.warn(
            "Difference detected for DECLARED_CLASSES field - expectedClass: {}, actualClass: {}",
            expectedClass.getDeclaredClasses(), actualClass.getDeclaredClasses());
        areEquals = false;
      }

      if (typeComparatorConfig.comparePackageName() && !expectedClass.getPackageName()
          .equals(actualClass.getPackageName())) {
        log.warn("Difference detected for PACKAGE_NAME field - expectedClass: {}, actualClass: {}",
            expectedClass.getPackageName(), actualClass.getPackageName());
        areEquals = false;
      }

      if (typeComparatorConfig.compareEnumConstants() && !Arrays.equals(
          expectedClass.getEnumConstants(), actualClass.getEnumConstants())) {
        log.warn(
            "Difference detected for ENUM_CONSTANTS field - expectedClass: {}, actualClass: {}",
            expectedClass.getEnumConstants(), actualClass.getEnumConstants());
        areEquals = false;
      }

      if (typeComparatorConfig.compareInterfaces() && !Arrays.equals(expectedClass.getInterfaces(),
          actualClass.getInterfaces())) {
        log.warn("Difference detected for INTERFACES field - expectedClass: {}, actualClass: {}",
            expectedClass.getInterfaces(), actualClass.getInterfaces());
        areEquals = false;
      }

      if (typeComparatorConfig.compareSuperClass() && !Objects.equals(expectedClass.getSuperClass(),
          actualClass.getSuperClass())) {
        log.warn("Difference detected for SUPERCLASS field - expectedClass: {}, actualClass: {}",
            expectedClass.getSuperClass(), actualClass.getSuperClass());
        areEquals = false;
      }

      if (typeComparatorConfig.compareIsPublic()
          && expectedClass.isPublic() != actualClass.isPublic()) {
        log.warn("Difference detected for IS_PUBLIC field - expectedClass: {}, actualClass: {}",
            expectedClass.isPublic(), actualClass.isPublic());
        areEquals = false;
      }

      if (typeComparatorConfig.compareIsPrivate()
          && expectedClass.isPrivate() != actualClass.isPrivate()) {
        log.warn("Difference detected for IS_PRIVATE field - expectedClass: {}, actualClass: {}",
            expectedClass.isPrivate(), actualClass.isPrivate());
        areEquals = false;
      }

      if (typeComparatorConfig.compareIsProtected()
          && expectedClass.isProtected() != actualClass.isProtected()) {
        log.warn("Difference detected for IS_PROTECTED field - expectedClass: {}, actualClass: {}",
            expectedClass.isProtected(), actualClass.isProtected());
        areEquals = false;
      }

      if (typeComparatorConfig.compareIsStatic()
          && expectedClass.isStatic() != actualClass.isStatic()) {
        log.warn("Difference detected for IS_STATIC field - expectedClass: {}, actualClass: {}",
            expectedClass.isStatic(), actualClass.isStatic());
        areEquals = false;
      }

      if (typeComparatorConfig.compareIsFinal()
          && expectedClass.isFinal() != actualClass.isFinal()) {
        log.warn("Difference detected for IS_FINAL field - expectedClass: {}, actualClass: {}",
            expectedClass.isFinal(), actualClass.isFinal());
        areEquals = false;
      }

      if (typeComparatorConfig.compareIsSynchronized()
          && expectedClass.isSynchronized() != actualClass.isSynchronized()) {
        log.warn(
            "Difference detected for IS_SYNCHRONIZED field - expectedClass: {}, actualClass: {}",
            expectedClass.isSynchronized(), actualClass.isSynchronized());
        areEquals = false;
      }

      if (typeComparatorConfig.compareIsVolatile()
          && expectedClass.isVolatile() != actualClass.isVolatile()) {
        log.warn("Difference detected for IS_VOLATILE field - expectedClass: {}, actualClass: {}",
            expectedClass.isVolatile(), actualClass.isVolatile());
        areEquals = false;
      }

      if (typeComparatorConfig.compareIsTransient()
          && expectedClass.isTransient() != actualClass.isTransient()) {
        log.warn("Difference detected for IS_TRANSIENT field - expectedClass: {}, actualClass: {}",
            expectedClass.isTransient(), actualClass.isTransient());
        areEquals = false;
      }

      if (typeComparatorConfig.compareIsNative()
          && expectedClass.isNative() != actualClass.isNative()) {
        log.warn("Difference detected for IS_NATIVE field - expectedClass: {}, actualClass: {}",
            expectedClass.isNative(), actualClass.isNative());
        areEquals = false;
      }

      if (typeComparatorConfig.compareIsInterface()
          && expectedClass.isInterface() != actualClass.isInterface()) {
        log.warn("Difference detected for IS_INTERFACE field - expectedClass: {}, actualClass: {}",
            expectedClass.isInterface(), actualClass.isInterface());
        areEquals = false;
      }

      if (typeComparatorConfig.compareIsAbstract()
          && expectedClass.isAbstract() != actualClass.isAbstract()) {
        log.warn("Difference detected for IS_ABSTRACT field - expectedClass: {}, actualClass: {}",
            expectedClass.isAbstract(), actualClass.isAbstract());
        areEquals = false;
      }

      if (typeComparatorConfig.compareIsStrict()
          && expectedClass.isStrict() != actualClass.isStrict()) {
        log.warn("Difference detected for IS_STRICT field - expectedClass: {}, actualClass: {}",
            expectedClass.isStrict(), actualClass.isStrict());
        areEquals = false;
      }

    }

    return areEquals;
  }

}
