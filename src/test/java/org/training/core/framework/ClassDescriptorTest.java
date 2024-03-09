package org.training.core.framework;

import static org.junit.Assert.*;

import java.util.Map;

import org.training.core.framework.typeDefs.ClassDef;
import org.training.core.framework.typeDefs.FieldDef;
import org.training.core.framework.typeDefs.MethodDef;
import java.lang.String;
import org.junit.Test;

public class ClassDescriptorTest {

  private static final String TEST_CLASS_NAME = "java.lang.String";

  @Test
  public void testClassDescriptor() throws Exception {
    ClassDescriptor classDescriptor = new ClassDescriptor(TEST_CLASS_NAME);

    assertEquals(TEST_CLASS_NAME, classDescriptor.getName());
    ClassDef classDef = classDescriptor.getClassDef();
    assertEquals(TEST_CLASS_NAME, classDef.getName());
    assertNotNull(classDef.getAnnotations());
    assertNotNull(classDef.getDeclaredClasses());
    assertEquals("java.lang", classDef.getPackageName());
    assertNull(classDef.getEnumConstants());
    assertNotNull(classDef.getInterfaces());
    assertEquals(Object.class, classDef.getSuperClass());
    assertTrue(classDef.isPublic());
    assertFalse(classDef.isPrivate());
    assertFalse(classDef.isProtected());
    assertFalse(classDef.isStatic());
    assertTrue(classDef.isFinal());
    assertFalse(classDef.isSynchronized());
    assertFalse(classDef.isVolatile());
    assertFalse(classDef.isNative());
    assertFalse(classDef.isInterface());
    assertFalse(classDef.isAbstract());
    assertFalse(classDef.isStrict());

    Map<String, FieldDef> fields = classDescriptor.getFields();
    assertNotNull(fields);
    assertFalse(fields.isEmpty());
    for (FieldDef fieldDef : fields.values()) {
      assertNotNull(fieldDef.getName());
      assertNotNull(fieldDef.getAnnotations());
      assertFalse(fieldDef.isPrimitive());
      assertTrue(fieldDef.isPublic() || fieldDef.isPrivate() || fieldDef.isProtected());
    }

    Map<String, MethodDef> methods = classDescriptor.getMethods();
    assertNotNull(methods);
    assertFalse(methods.isEmpty());
    for (MethodDef methodDef : methods.values()) {
      assertNotNull(methodDef.getName());
      assertNotNull(methodDef.getAnnotations());
      assertNotNull(methodDef.getParameters());
      assertTrue(methodDef.isPublic() || methodDef.isPrivate() || methodDef.isProtected());
    }
  }

  @Test(expected = NullPointerException.class)
  public void testClassDescriptorWithNull() {
    new ClassDescriptor(null);
  }

  @Test(expected = ClassNotFoundException.class)
  public void testClassDescriptorWithEmptyString() {
    new ClassDescriptor("");
  }

  @Test
  public void testExecuteMethod() {
    ClassDescriptor classDescriptor = new ClassDescriptor("java.util.ArrayList");
    Object result = classDescriptor.executeMethod("size");
    assertEquals(0, result);
  }

}