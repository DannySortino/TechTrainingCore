package org.training.core.framework;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.boot.SpringApplication;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

public class SpringStartTest {
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        // Setup if necessary
    }

    // Inner class to serve as a test fixture
    public static class ExampleClass {
        public static void main(String[] args) {
            SpringApplication.run(ExampleClass.class, args);
            System.out.println("Hello from ExampleClass main method");
        }
    }

    @Test
    public void testStart_withValidClassAndMethod() {
        // The class name of the inner class for reflection
        String className = ExampleClass.class.getName();
        try {
            // Assuming start method does not actually return anything for this example
            SpringStart.start(className);
            // If the method invocation was successful, we pass the test
        } catch (Exception e) {
            fail("Expected no exception, but got: " + e.getMessage());
        }
    }

    @Test(expected = RuntimeException.class)
    public void testStart_withInvalidClassName() {
        SpringStart.start("com.nonexistent.FakeClass");
    }

    @Test(expected = RuntimeException.class)
    public void testStart_withValidClassButNoMainMethod() {
        // Use a standard Java class that definitely does not have a 'main' method
        SpringStart.start("java.lang.String");
    }


}
