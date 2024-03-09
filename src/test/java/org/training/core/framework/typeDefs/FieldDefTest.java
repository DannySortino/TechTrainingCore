package org.training.core.framework.typeDefs;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class FieldDefTest {
    private FieldDef fieldDef;

    @Before
    public void setUp() {
        fieldDef = FieldDef.builder()
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
        assertEquals("Test", fieldDef.getName());
    }

    @Test
    public void testIsPublic() {
        assertTrue(fieldDef.isPublic());
    }

    @Test
    public void testIsPrivate() {
        assertFalse(fieldDef.isPrivate());
    }

    @Test
    public void testIsProtected() {
        assertFalse(fieldDef.isProtected());
    }

    @Test
    public void testIsStatic() {
        assertFalse(fieldDef.isStatic());
    }

    @Test
    public void testIsFinal() {
        assertFalse(fieldDef.isFinal());
    }

    @Test
    public void testIsSynchronized() {
        assertFalse(fieldDef.isSynchronized());
    }

    @Test
    public void testIsVolatile() {
        assertFalse(fieldDef.isVolatile());
    }

    @Test
    public void testIsTransient() {
        assertFalse(fieldDef.isTransient());
    }

    @Test
    public void testIsNative() {
        assertFalse(fieldDef.isNative());
    }

    @Test
    public void testIsInterface() {
        assertFalse(fieldDef.isInterface());
    }

    @Test
    public void testIsAbstract() {
        assertFalse(fieldDef.isAbstract());
    }

    @Test
    public void testIsStrict() {
        assertFalse(fieldDef.isStrict());
    }

}
