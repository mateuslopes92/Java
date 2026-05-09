# Spring Web App Demo Project

This project demonstrates how to build a web application using Spring Boot:

- Java 25
- Maven
- Spring Boot 4.0.6
- Spring Web MVC (Web application)
- Spring DevTools (Auto-restart, LiveReload)

## Introduction to Web Applications with Spring

Transition from console-based applications to web applications.
Overview of client-server architecture and database interaction.

### Client Types

Web applications and mobile applications as clients.
The role of the client in sending requests and receiving data.

### Data Handling

Modern applications primarily send data (JSON/XML) rather than layout.
Separation of front-end (React/Angular) and back-end (Spring) responsibilities.

## Creating a Spring Web Project

Setting up a Spring Boot project with web capabilities.

### Project Setup

```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>4.0.6</version>
</parent>

<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-webmvc</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
        <scope>runtime</scope>
        <optional>true</optional>
    </dependency>
</dependencies>
```

Key dependencies:
- `spring-boot-starter-webmvc` — Core Spring MVC for building web applications
- `spring-boot-devtools` — Developer tools (auto-restart, LiveReload)

### Application Entry Point

```java
package com.mateuslopes.SpringWebApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringWebAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringWebAppApplication.class, args);
    }

}
```

## Controller Creation

Controllers handle client requests. Spring provides annotations like `@Controller` and `@RestController` to define request handling.

### RestController Example

```java
package com.mateuslopes.SpringWebApp;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @RequestMapping("/")
    public String greet() {
        return "Welcome to java web!";
    }

    @RequestMapping("/about")
    public String about() {
        return "About page!";
    }
}
```

`@RestController` combines `@Controller` and `@ResponseBody`, meaning methods return data directly (JSON/XML/String) rather than view templates.

## Request Mapping

`@RequestMapping` maps HTTP requests to handler methods by URL path.

```java
@RequestMapping("/login")
public String login() {
    return "Login page!";
}
```

Returns data instead of HTML pages.

## Multiple Controllers

Multiple controllers can handle different functionalities. The front controller (`DispatcherServlet`) manages requests to the appropriate controller.

```java
@RestController
public class LoginController {

    @RequestMapping("/login")
    public String login() {
        return "Login page!";
    }
}
```

## Application Configuration

```properties
spring.application.name=SpringWebApp
```

Only the application name is configured — Spring Boot auto-configuration handles the rest.

## Key Takeaways

- The Spring Framework allows for the development of robust web applications by separating concerns between the client and server.
- Modern applications typically utilize a RESTful approach, focusing on data transfer rather than full page rendering.
- Controllers are essential for managing requests and responses, and Spring provides powerful annotations to simplify this process.
- The architecture supports multiple controllers, enhancing modularity and maintainability.
- `@RestController` simplifies REST API development by combining `@Controller` and `@ResponseBody`.

## Lessons Learned

- Understanding the client-server model is crucial for web development.
- Familiarity with JSON and XML is important for data representation in web applications.
- Properly configuring a Spring Boot project is essential for leveraging its capabilities.
- The use of annotations in Spring simplifies the process of request handling and improves code readability.
- The concept of a front controller is vital for routing requests to the correct handler in a web application.
- Spring Boot's auto-configuration eliminates boilerplate, letting you focus on business logic.
