# Spring Core Demo Project
I've just created this project using `https://start.spring.io/` with:
- Java 25
- Maven
- Jar
- Spring Boot (4.0.6)
- Dependency(Spring Web only)

During the development i will be adding more dependencies.

## Spring Framework
Spring Framework is a Java framework used to build applications in a simpler and more organized way.

It provides features like:
- Dependency Injection (DI)
- Inversion of Control (IoC)
- Web development support
- Database integration
- Security and testing tools

Spring helps developers write clean, modular, and maintainable code.

## Spring IoC and DI
IoC and DI are fundamental concepts in the Spring Framework that help manage object creation and dependencies.

### IoC (Inversion of Control)
- Definition of IoC: A principle where the control of object creation is transferred from the developer to an external entity (like Spring).
- Importance of IoC in simplifying application development by managing object lifecycle.

### DI (Dependency Injection)
- Definition of DI: A concrete implementation of IoC that allows objects to be injected into a class rather than being created within it.
- Clarification of the difference between IoC (a principle) and DI (a design pattern).

#### Techniques for Dependency Injection
- Constructor Injection: Passing dependencies through a class constructor.
- Setter Injection: Using setter methods to inject dependencies.
- Field Injection: Directly injecting dependencies into fields (less recommended due to tight coupling).

#### Dependency Injection(ApplicationContext and Component)
- simplifies object management by allowing Spring to handle the lifecycle and dependencies of objects.
- Annotations like `@Component` allows developers to easily define which classes should be managed by Spring.
- Understanding how to interact with the ApplicationContext is essential for retrieving beans and managing dependencies effectively.

##### Example:
```java
import org.springframework.stereotype.Component;

@Component
public class Dev {
    public void build(){
        System.out.println("Building awesome things!");
    }
}
```
```java
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpringCoreDemoApplication {

	public static void main(String[] args) {
		ApplicationContext context;

		context = SpringApplication.run(SpringCoreDemoApplication.class, args);

		Dev obj = context.getBean(Dev.class);

		obj.build();

	}

}
```

## Spring Boot
Spring Boot is a tool built on top of Spring Framework that makes Spring applications easier and faster to create.

Main features:
- Auto-configuration
- Embedded servers like Tomcat
- Starter dependencies
- Less configuration

Spring Boot reduces boilerplate code and helps developers start projects quickly.


## AutoWiring
- Auto Wiring: Simplifies the process of connecting dependencies in Spring Boot applications.
- Resolving Ambiguity: When multiple beans are present, use `@Primary` to indicate a preferred bean or `@Qualifier` to specify which bean to use.

We have 3 types of Dependency Injection:
- Field Injection: Using `@Autowired` directly on fields.
- Constructor Injection: Passing dependencies through the constructor.
- Setter Injection: Using setter methods to inject dependencies.

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class Dev {

    @Autowired // Field injection (Connects the 2 components each other)
    @Qualifier("laptop") // in case of confusion on 2 classes with @Component we can choose here
    private Computer computer;

//    via Constructor(default) works as well as option instead of Autowired
//    public Dev(Computer laptop){
//        this.computer = computer;
//    }

//    @Autowired // Setter Injection
//    public void setLaptop(Computer computer){
//        this.computer = computer;
//    }

    public void build(){

        computer.compile();

        System.out.println("Building awesome things!");
    }
}
```

### Handling Multiple Implementations
- Issues that arise when multiple beans of the same type exist.
- Solutions using: `@Primary` and `@Qualifier` annotations to resolve ambiguity.

#### @Primary
```java
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
//@Primary // in case of confusion on 2 classes with @Component we can choose here the primary
public class Laptop implements Computer {

    public void compile(){
        System.out.println("Compile with 404 bugs");
    }

}
```

#### @Qualifier
```java
    @Autowired // Field injection (Connects the 2 components each other)
    @Qualifier("laptop") // in case of confusion on 2 classes with @Component we can choose here
    private Computer computer;
```