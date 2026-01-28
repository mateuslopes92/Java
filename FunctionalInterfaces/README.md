# Java Functional Interfaces
A functional interface in Java is an interface that has only one abstract method, making it suitable for use with lambda expressions and method references (introduced in Java 8).


- Use @FunctionalInterface to ensure only one abstract method (annotation is optional).
- Enable clean, concise code using lambdas and method references.

Example: 
```
public class Geeks {

    public static void main(String[] args) {
      
        // Using lambda expression to implement Runnable
        new Thread(() -> System.out.println("New thread created")).start();
    }
    
}
```

## Types of Functional Interfaces in Java
Java 8 introduced four main functional interface types under the package java.util.function. These are widely used in Stream API, collections and lambda-based operations.
- Function
- Consumer
- Predicate
- Supplier

