package org.training.core.framework.typeDefs;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;

public class MethodDefTest {
    private MethodDef methodDef;

    @Before
    public void setUp() {
        methodDef = MethodDef.builder()
                .name("Test")
                .isPublic(true)
                .isPrivate(false)
                .isProtected(false)
                .isStatic(false)
                .isFinal(false)
                .isSynchronized(false)
                .isVolatile(false)
                .isTransient(false)
                .isNative(false)
                .isInterface(false)
                .isAbstract(false)
                .isStrict(false)
                .build();
    }

    @Test
    public void testName() {
        assertEquals("Test", methodDef.getName());
    }

    @Test
    public void testIsPublic() {
        assertTrue(methodDef.isPublic());
    }

    @Test
    public void testIsPrivate() {
        assertFalse(methodDef.isPrivate());
    }

    @Test
    public void testIsProtected() {
        assertFalse(methodDef.isProtected());
    }

    @Test
    public void testIsStatic() {
        assertFalse(methodDef.isStatic());
    }

    @Test
    public void testIsFinal() {
        assertFalse(methodDef.isFinal());
    }

    @Test
    public void testIsSynchronized() {
        assertFalse(methodDef.isSynchronized());
    }

    @Test
    public void testIsVolatile() {
        assertFalse(methodDef.isVolatile());
    }

    @Test
    public void testIsTransient() {
        assertFalse(methodDef.isTransient());
    }

    @Test
    public void testIsNative() {
        assertFalse(methodDef.isNative());
    }

    @Test
    public void testIsInterface() {
        assertFalse(methodDef.isInterface());
    }

    @Test
    public void testIsAbstract() {
        assertFalse(methodDef.isAbstract());
    }

    @Test
    public void testIsStrict() {
        assertFalse(methodDef.isStrict());
    }
}
