# Project Overview

This project is a Java-based library that provides a powerful framework for introspecting and analyzing Java code at runtime. It leverages the Spring framework and Maven for dependency management. The library is designed to be used as a training tool, allowing trainers to not only verify the output of trainee's code but also the methods and procedures they used to achieve it.

## Use Cases

1. **Code Introspection**: The library allows for dynamic introspection of Java classes at runtime. This includes the ability to investigate fields, methods, and classes. It can also interact with Spring beans, allowing you to query their values at runtime.

2. **Training Framework**: The library can be used as a training tool. It allows trainers to verify that trainees are not only achieving the correct output but also using the correct methods and procedures. This can be used to ensure that trainees are following best practices and correctly applying the concepts they have learned.

3. **Method Verification**: The library can store a stack trace of all method calls made. This can be used to verify the sequence of method calls made by a trainee, ensuring they are following the correct procedures.

## How to Use

1. **Clone the Repository**: Clone the repository to your local machine using Git.

2. **Build the Project**: Navigate to the project directory and build the project using Maven with the command `mvn clean install`.

3. **Run the Application**: Run the application using the `SpringStart` class. You can specify the class name and any arguments as parameters to the `start` method.

4. **Use the Features**: Use the features of the library as needed. For example, you can create a `ClassDescriptor` object to introspect a class, use the `TypeComparator` class to compare classes, or use the `GitRepositoryInfo` class to interact with a Git repository.

Please refer to the individual Java files for more detailed information on how to use each class and method.

## Running Tests

Due to the introspection in the code and the fact that it uses AspectJ weaver, to run the tests it will need to be run using the following arguments:

```
--add-opens=java.base/java.lang=ALL-UNNAMED
-javaagent:C:\Users\danny\.m2\repository\org/aspectj/aspectjweaver/1.9.8.RC1/aspectjweaver-1.9.8.RC1.jar
```

## Example

Here is an example of how you can use this library for a training scenario:

```java
// Create a ClassDescriptor for the class you want to introspect
ClassDescriptor classDescriptor = new ClassDescriptor("com.example.MyClass");

// Get the fields of the class
Map<String, FieldDef> fields = classDescriptor.getFields();

// Get the methods of the class
Map<String, MethodDef> methods = classDescriptor.getMethods();

// Execute a method
Object result = classDescriptor.executeMethod("myMethod", new Pair(String.class, "myArg"));

// Get the invocation count of the methods
Map<String, Integer> methodInvocationCount = classDescriptor.getMethodInvocationCount();

// Compare two classes
TypeComparator typeComparator = new TypeComparator(new TypeComparatorConfig().enableAll());
boolean areClassesEqual = typeComparator.equals(expectedClassDef, actualClassDef);
```

This library provides a powerful tool for introspecting and analyzing Java code, making it a valuable resource for training scenarios.