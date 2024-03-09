package org.training.core.framework.typeDefs;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ClassDefTest {
    private ClassDef classDef;

    @Before
    public void setUp() {
        classDef = ClassDef.builder()
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
        assertEquals("Test", classDef.getName());
    }

    @Test
    public void testIsPublic() {
        assertTrue(classDef.isPublic());
    }

    @Test
    public void testIsPrivate() {
        assertFalse(classDef.isPrivate());
    }

    @Test
    public void testIsProtected() {
        assertFalse(classDef.isProtected());
    }

    @Test
    public void testIsStatic() {
        assertFalse(classDef.isStatic());
    }

    @Test
    public void testIsFinal() {
        assertFalse(classDef.isFinal());
    }

    @Test
    public void testIsSynchronized() {
        assertFalse(classDef.isSynchronized());
    }

    @Test
    public void testIsVolatile() {
        assertFalse(classDef.isVolatile());
    }

    @Test
    public void testIsTransient() {
        assertFalse(classDef.isTransient());
    }

    @Test
    public void testIsNative() {
        assertFalse(classDef.isNative());
    }

    @Test
    public void testIsInterface() {
        assertFalse(classDef.isInterface());
    }

    @Test
    public void testIsAbstract() {
        assertFalse(classDef.isAbstract());
    }

    @Test
    public void testIsStrict() {
        assertFalse(classDef.isStrict());
    }


}
