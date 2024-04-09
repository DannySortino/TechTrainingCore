package org.training.core.framework;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javassist.Modifier;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.training.core.framework.config.SpringContext;
import org.training.core.framework.typeDefs.ClassDef;
import org.training.core.framework.typeDefs.FieldDef;
import org.training.core.framework.typeDefs.MethodDef;
import org.training.core.framework.utils.MethodInvocationCounterAspect;
import org.training.core.framework.utils.WrapperTypes;

@Slf4j
public class ClassDescriptor {

  @Getter
  private final String name;
  @Getter
  private final ClassDef classDef;
  private final Class<?> clazz;
  private Object instancedObject;

  @SneakyThrows
  public ClassDescriptor(String name, Object... args) {
    Objects.requireNonNull(name, "Class name cannot be null");
    this.name = name;
    clazz = getRealClass(name);
    ensureInstantiatedClass(args);
    classDef = createClassDef(clazz);
  }

  @SneakyThrows
  public ClassDescriptor(String name, Object instancedObject) {
    Objects.requireNonNull(name, "Class name cannot be null");
    this.name = name;
    clazz = getRealClass(name);

    this.instancedObject = instancedObject;
    if (!WrapperTypes.isWrapperType(clazz)) {
      ensureInstantiatedClass();
    }
    classDef = createClassDef(clazz);
  }

  private static Class<?> getRealClass(String name) throws ClassNotFoundException {
    return Class.forName(name);
  }

  private ClassDef createClassDef(Class<?> realClass) {
    return ClassDef.builder().name(name).annotations(realClass.getDeclaredAnnotations())
        .declaredClasses(realClass.getDeclaredClasses()).packageName(clazz.getPackageName())
        .enumConstants(realClass.getEnumConstants()).interfaces(realClass.getInterfaces())
        .superClass(realClass.getSuperclass()).isPublic(Modifier.isPublic(realClass.getModifiers()))
        .isPrivate(Modifier.isPrivate(realClass.getModifiers()))
        .isProtected(Modifier.isProtected(realClass.getModifiers()))
        .isStatic(Modifier.isStatic(realClass.getModifiers()))
        .isFinal(Modifier.isFinal(realClass.getModifiers()))
        .isSynchronized(Modifier.isSynchronized(realClass.getModifiers()))
        .isVolatile(Modifier.isVolatile(realClass.getModifiers()))
        .isNative(Modifier.isNative(realClass.getModifiers()))
        .isInterface(Modifier.isInterface(realClass.getModifiers()))
        .isAbstract(Modifier.isAbstract(realClass.getModifiers()))
        .isStrict(Modifier.isStrict(realClass.getModifiers())).build();
  }

  @SafeVarargs
  @SneakyThrows
  public final Object executeMethod(String methodName, Pair<Class<?>, Object>... args) {
    Lookup lookup = MethodHandles.lookup();
    MethodHandle methodHandle;
    Method method;

    Class<?>[] argTypes = Objects.isNull(args) ? null
        : Arrays.stream(args).map(Pair::getLeft).toArray(Class<?>[]::new);
    Object[] argValues = Objects.isNull(args) ? null
        : Arrays.stream(args).map(Pair::getRight).toArray(Object[]::new);

    try {
      method = clazz.getDeclaredMethod(methodName, argTypes);
      method.setAccessible(true);
      methodHandle = lookup.unreflect(method);
    } catch (NoSuchMethodException | IllegalAccessException e) {
      log.error("You are likely missing the expected method - {}", methodName, e);
      throw e;
    }

    if (Modifier.isStatic(method.getModifiers())) {
      return methodHandle.invokeWithArguments(argValues);
    }
    return methodHandle.bindTo(instancedObject).invokeWithArguments(argValues);
  }


  @SneakyThrows
  public Map<String, FieldDef> getFields() {
    Field[] fields = clazz.getDeclaredFields();
    Map<String, FieldDef> fieldDefs = new HashMap<>();

    for (Field field : fields) {
      if (field.trySetAccessible()) {
        fieldDefs.put(field.getName(), createFieldDef(field));
      } else {
        log.warn("Unable to set field {} accessible", field.getName());
      }
    }

    return fieldDefs;
  }

  private FieldDef createFieldDef(Field field) throws IllegalAccessException {
    return FieldDef.builder().name(field.getName())
        .value(Objects.isNull(instancedObject) ? null : field.get(instancedObject)).classDescriptor(
            field.getType().isPrimitive() ? null
                : new ClassDescriptor(field.getType().getName(), field.get(instancedObject)))
        .annotations(field.getDeclaredAnnotations()).isPrimitive(field.getType().isPrimitive())
        .isPublic(Modifier.isPublic(field.getModifiers()))
        .isPrivate(Modifier.isPrivate(field.getModifiers()))
        .isProtected(Modifier.isProtected(field.getModifiers()))
        .isStatic(Modifier.isStatic(field.getModifiers()))
        .isFinal(Modifier.isFinal(field.getModifiers()))
        .isSynchronized(Modifier.isSynchronized(field.getModifiers()))
        .isVolatile(Modifier.isVolatile(field.getModifiers()))
        .isNative(Modifier.isNative(field.getModifiers()))
        .isInterface(Modifier.isInterface(field.getModifiers()))
        .isAbstract(Modifier.isAbstract(field.getModifiers()))
        .isStrict(Modifier.isStrict(field.getModifiers())).build();
  }

  public Map<String, MethodDef> getMethods() {
    Method[] methods = clazz.getDeclaredMethods();
    Map<String, MethodDef> methodDefs = new HashMap<>();

    for (Method method : methods) {
      if (method.trySetAccessible()) {
        methodDefs.put(method.getName(), createMethodDef(method));
      } else {
        log.warn("Unable to set method {} accessible", method.getName());
      }
    }

    return methodDefs;
  }

  private MethodDef createMethodDef(Method method) {
    return MethodDef.builder().name(method.getName()).parameters(method.getParameters())
        .annotations(method.getDeclaredAnnotations())
        .isPublic(Modifier.isPublic(method.getModifiers()))
        .isPrivate(Modifier.isPrivate(method.getModifiers()))
        .isProtected(Modifier.isProtected(method.getModifiers()))
        .isStatic(Modifier.isStatic(method.getModifiers()))
        .isFinal(Modifier.isFinal(method.getModifiers()))
        .isSynchronized(Modifier.isSynchronized(method.getModifiers()))
        .isVolatile(Modifier.isVolatile(method.getModifiers()))
        .isNative(Modifier.isNative(method.getModifiers()))
        .isInterface(Modifier.isInterface(method.getModifiers()))
        .isAbstract(Modifier.isAbstract(method.getModifiers()))
        .isStrict(Modifier.isStrict(method.getModifiers())).build();
  }

  @SneakyThrows
  @SuppressWarnings("unchecked")
  public Map<String, Integer> getMethodInvocationCount() {

    try {
      return MethodInvocationCounterAspect.getMethodInvocationCount().entrySet().stream()
          .filter(this::isSameClassAsCurrent).collect(
              Collectors.toMap(k -> k.getKey().substring(k.getKey().lastIndexOf(".") + 1),
                  Map.Entry::getValue));
    } catch (Exception e) {
      // This is expected if the class does not have the method
    }
    return new HashMap<>();
  }

  private boolean isSameClassAsCurrent(Map.Entry<String, Integer> entry) {
    String packageAndModuleName = entry.getKey().substring(0, entry.getKey().lastIndexOf("."));

    return packageAndModuleName.equals(clazz.getName());
  }

  @SneakyThrows
  private void ensureInstantiatedClass(Object... args) {
    if (Objects.isNull(instancedObject)) {
      if (!Objects.isNull(SpringContext.getBean(clazz))) {
        instancedObject = SpringContext.getBean(clazz);
      } else if (!Objects.isNull(SpringContext.getBean(name))) {
        instancedObject = SpringContext.getBean(name);
      } else if (!Objects.isNull(SpringContext.getBean(formattedBeanName()))) {
        instancedObject = SpringContext.getBean(formattedBeanName());
      } else {
        instancedObject = clazz.getDeclaredConstructor().newInstance(args);
      }
    }
  }

  // The name of this object is the fully qualified class name, we need to convert this to a bean name
  // EG: com.example.MyClass -> myClass
  private String formattedBeanName() {
    String className = name.substring(name.lastIndexOf('.') + 1);
    return Character.toLowerCase(className.charAt(0)) + className.substring(1);
  }
}
