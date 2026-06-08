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

Spring Security is the **standard for securing Spring-based applications**.

Key concepts:
- **Authentication** – verifying who the user is (login)
- **Authorization** – checking what the user can access (roles/permissions)
- **Security Filters** – intercepts requests before they reach controllers
- **Security Context** – holds the authenticated user's information

---

## Default Behavior (No Custom Config)

By default, Spring Security:
- Protects **all endpoints**
- Requires **basic authentication** for every request
- Generates a random password at startup (logged in the console)
- Default username is `user`

When you start the application, you'll see in the logs:
```
Using generated security password: <random-uuid>
```

---

## Basic Controller Example

```java
@RestController
public class HelloController {

    @GetMapping("/")
    public String greeting(){
        return "Hello Security";
    }
}
```

With Spring Security on the classpath, accessing `GET /` will prompt for credentials before returning "Hello Security".

---

## How Spring Security Works (Filter Chain)

Spring Security works through a **chain of filters** that intercept HTTP requests:

```
Request → SecurityFilterChain → Controller → Response
```

Each filter in the chain checks something:
- Authentication filters check if the user is logged in
- Authorization filters check if the user has permission
- If a filter fails, the request is rejected (401 Unauthorized or 403 Forbidden)

---

## Key Components

### SecurityFilterChain

Defines **which requests are secured** and **how**.

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .anyRequest().authenticated()
            )
            .formLogin(Customizer.withDefaults())
            .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
```

### AuthenticationManager

Handles the **authentication logic** (validating username/password).

---

## Summary

Spring Security provides:
- **Default security** out of the box (no config needed to get started)
- **Customizable security rules** via `SecurityFilterChain`
- **Multiple authentication methods** (form login, HTTP Basic, OAuth2, etc.)
- **Role-based authorization** to restrict access by user roles

As the course progresses, more topics will be added:
- In-memory and JDBC user details
- Custom login pages
- CSRF protection
- Method-level security
- JWT and OAuth2
