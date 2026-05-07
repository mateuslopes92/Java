# Spring Without Boot Demo Project

This project demonstrates how to use the Spring Framework without Spring Boot, with manual configuration:

- Java 25
- Maven
- Jar
- Spring Context (7.0.7) - added manually
- XML-based bean configuration

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

#### Dependency Injection(ApplicationContext and XML Configuration)
- Spring manages object creation and dependencies through XML configuration instead of annotations.
- Beans are defined explicitly in `spring.xml` using `<bean>` tags with class names and IDs.
- Understanding how to interact with the ApplicationContext is essential for retrieving beans and managing dependencies effectively.

##### Example:
```java
public class Dev {

    private Laptop laptop;

    public void setLaptop(Laptop laptop) {
        this.laptop = laptop;
    }

    public void build(){
        System.out.println("Building awesome things!");
        laptop.compile();
    }
}
```
```java
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");

        Dev obj = (Dev) context.getBean("dev");

        obj.build();
    }
}
```

## XML Configuration

Without Spring Boot's auto-configuration, beans must be defined manually in an XML file.

Main concepts:
- `<beans>` root element with Spring schema declaration
- `<bean>` tags to define each managed object
- `id` attribute for bean identification
- `class` attribute with the fully qualified class name
- `<property>` for setter injection
- `<constructor-arg>` for constructor injection

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="
        http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="dev" class="com.mateuslopes.Dev">
        <property name="laptop" ref="laptop" />
    </bean>

    <bean id="laptop" class="com.mateuslopes.Laptop">
    </bean>
</beans>
```

### Setter Injection

Using `<property>` tag to inject dependencies via setter methods:

```xml
<bean id="dev" class="com.mateuslopes.Dev">
    <property name="laptop" ref="laptop" />
    <property name="age" value="12" /> <!-- primitive type injection -->
</bean>
```

### Constructor Injection

Using `<constructor-arg>` tag to inject dependencies via constructors:

```xml
<bean id="dev" class="com.mateuslopes.Dev">
    <constructor-arg value="30" />
</bean>
```

### Handling References vs Values

- `ref` attribute: Injects another bean (reference type)
- `value` attribute: Injects primitive values (int, String, etc.)

```xml
<property name="laptop" ref="laptop" /> <!-- reference injection -->
<property name="age" value="12" /> <!-- primitive injection -->
```

## Auto Wiring

Auto wiring lets Spring automatically resolve and inject dependencies without explicitly defining `<property>` or `<constructor-arg>` tags.

### Using Interfaces

Programming to interfaces makes your code more flexible and decoupled:

```java
public interface Computer {
    void compile();
}

public class Laptop implements Computer {
    public void compile() {
        System.out.println("Compiling on Laptop!");
    }
}

public class Desktop implements Computer {
    public void compile() {
        System.out.println("Compiling on Desktop!");
    }
}
```

Now `Dev` depends on the `Computer` interface instead of a concrete class:

```java
public class Dev {
    private Computer computer;

    public void setComputer(Computer computer) {
        this.computer = computer;
    }

    public void build() {
        System.out.println("Building awesome things!");
        computer.compile();
    }
}
```

### Auto Wiring Modes in XML

You can enable auto wiring directly in the `<bean>` tag using the `autowire` attribute:

**byType** - Spring matches the dependency by its type:
```xml
<bean id="dev" class="com.mateuslopes.Dev" autowire="byType" />
<bean id="laptop" class="com.mateuslopes.Laptop" />
```

**byName** - Spring matches the dependency by the property name:
```xml
<bean id="dev" class="com.mateuslopes.Dev" autowire="byName" />
<bean id="computer" class="com.mateuslopes.Laptop" /> <!-- name must match the property -->
```

### Handling Multiple Implementations

When multiple beans of the same type exist (e.g., `Laptop` and `Desktop`), Spring throws an error because it doesn't know which one to inject.

**Solution**: Define only one bean of that type, or use `primary="true"` to mark a default:

```xml
<bean id="laptop" class="com.mateuslopes.Laptop" primary="true" />
<bean id="desktop" class="com.mateuslopes.Desktop" />
```

### Retrieving Beans by Type

You can also retrieve beans using their type instead of their ID:

```java
Computer comp = context.getBean(Computer.class);
comp.compile();
```

## Key Differences: Spring vs Spring Boot

| Feature | Spring Framework | Spring Boot |
|---------|------------------|-------------|
| Configuration | Manual (XML or annotations) | Auto-configuration |
| Setup | Add dependencies manually | Starter dependencies |
| Server | External server required | Embedded servers (Tomcat) |
| Boilerplate | More configuration code | Minimal configuration |

## Lessons Learned

- **Manual Configuration**: Spring Framework requires explicit bean definitions, providing deeper insights into how Spring operates.
- **Application Context**: Understanding `ClassPathXmlApplicationContext` is essential for loading XML configurations.
- **Dependency Injection**: Spring's core feature simplifies object creation and dependency management.
- **Error Handling**: Common issues include missing XML files, incorrect bean definitions, or wrong class paths.
