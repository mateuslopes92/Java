# Spring Security Study Project

Spring Security is a framework that provides **authentication** (who are you?) and **authorization** (what are you allowed to do?) for Java applications.

This project was created using `https://start.spring.io/` with:
- Java 25
- Maven
- Jar
- Spring Boot (4.0.6)
- Dependencies: Spring Security, Spring Web MVC, DevTools

---

## What is Spring Security?

Spring Security is a framework for **authentication** (who are you?) and **authorization** (what can you do?) in Java applications.

It works through a **chain of filters** that intercept every HTTP request before it reaches the controller:

```
Request → Security Filters → Controller → Response
```

Each filter checks something (login state, permissions, etc.). If a check fails, the request is rejected.

---

## What's in the code

### SpringSecurityApplication.java
```java
@SpringBootApplication
public class SpringSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityApplication.class, args);
	}

}
```

### HelloController.java
```java
@RestController
public class HelloController {

    @GetMapping("/")
    public String greeting(){
        return "Hello Security";
    }
}
```

With Spring Security on the classpath, the `/` endpoint is automatically protected and requires authentication to access.
