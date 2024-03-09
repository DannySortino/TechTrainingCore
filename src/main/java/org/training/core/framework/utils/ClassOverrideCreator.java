package org.training.core.framework.utils;

import static org.training.core.framework.Constants.METHOD_INVOCATION_COUNT;
import static org.training.core.framework.Constants.UNIQUE_CUSTOM_METHOD_NAME;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewConstructor;
import javassist.CtNewMethod;
import javassist.NotFoundException;
import lombok.SneakyThrows;
import org.apache.dubbo.common.utils.ReflectUtils;

public class ClassOverrideCreator {

  @SneakyThrows
  public static Class<?> createCustomClass(String name, Class<?> realClass) {
    final Class<?> customClass;

    CtClass origClazz = ClassPool.getDefault().get(name);
    CtClass subClass = ClassPool.getDefault()
        .makeClass(origClazz.getName() + UNIQUE_CUSTOM_METHOD_NAME);

    subClass.setSuperclass(origClazz);

    addInvocationCountField(subClass);
    copyFields(realClass, origClazz, subClass);
    addMethodInvocationCountToMethods(realClass, origClazz, subClass);
    copyAttributes(origClazz, subClass);
    copyConstructors(origClazz, subClass);

    //subClass.writeFile("target/classes");

    customClass = subClass.toClass();
    return customClass;
  }

  private static void addInvocationCountField(CtClass subClass) throws CannotCompileException {
    CtField f = CtField.make("public static java.util.HashMap " + METHOD_INVOCATION_COUNT + ";",
        subClass);
    subClass.addField(f, CtField.Initializer.byExpr("new java.util.HashMap()"));
  }

  private static void copyFields(Class<?> realClass, CtClass origClazz, CtClass subClass)
      throws NotFoundException, CannotCompileException {
    for (Field field : realClass.getDeclaredFields()) {
      CtField fieldToCopy = new CtField(origClazz.getDeclaredField(field.getName()), subClass);
      subClass.addField(fieldToCopy);
    }

  }

  private static void addMethodInvocationCountToMethods(Class<?> realClass, CtClass origClazz,
      CtClass subClass) throws NotFoundException, CannotCompileException {
    for (Method method : realClass.getDeclaredMethods()) {
      CtMethod m = CtNewMethod.copy(
          origClazz.getMethod(method.getName(), ReflectUtils.getDescWithoutMethodName(method)),
          subClass, null);

      //Note to self. Had to do gross conversion of Integer to int and back to Integer else it would cast to string and concat
      m.insertBefore(
          "if(!" + METHOD_INVOCATION_COUNT + ".containsKey(\"" + method.getName() + "\")){\n" + "  "
              + METHOD_INVOCATION_COUNT + ".put(\"" + method.getName()
              + "\", Integer.valueOf(1));\n" + "} else {\n" + "  int newValue = ((Integer)"
              + METHOD_INVOCATION_COUNT + ".get(\"" + method.getName() + "\")).intValue() + 1;\n"
              + "  " + METHOD_INVOCATION_COUNT + ".put(\"" + method.getName()
              + "\", Integer.valueOf(newValue));\n" + "}");
      subClass.addMethod(m);
    }
  }

  private static void copyAttributes(CtClass origClazz, CtClass subClass) {
    origClazz.getClassFile().getAttributes().forEach(attr -> subClass.getClassFile()
        .addAttribute(attr.copy(subClass.getClassFile().getConstPool(), null)));
  }

  private static void copyConstructors(CtClass origClazz, CtClass subClass)
      throws CannotCompileException {
    for (CtConstructor originalConstruct : origClazz.getConstructors()) {
      CtConstructor copy = CtNewConstructor.copy(originalConstruct, subClass, null);
      subClass.addConstructor(copy);
    }
  }


}
