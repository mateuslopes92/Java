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

---

## CSRF Protection

Spring Security enables **CSRF protection by default** for state-changing requests (POST, PUT, DELETE, PATCH). This project demonstrates CSRF with a `Student` resource.

### Student.java
```java
public class Student {
    private int id;
    private String name;
    private int marks;

    public Student(int id, String name, int marks) {
        this.id = id;
        this.name = name;
        this.marks = marks;
    }

    // getters and setters ...
}
```

### StudentController.java
```java
@RestController
public class StudentController {

    private List<Student> students = new ArrayList<>(
            List.of(
                    new Student(1, "Mateus", 60),
                    new Student(2, "Lucas", 65)
            ));

    @GetMapping("/students")
    public List<Student> getStudents() {
        return students;
    }

    @GetMapping("/csrf-token")
    public CsrfToken getCsrfToken(HttpServletRequest request) {
        return (CsrfToken) request.getAttribute("_csrf");
    }

    @PostMapping("/students")
    public Student addStudent(@RequestBody Student student) {
        students.add(student);
        return student;
    }
}
```

### How CSRF works here

- `GET /students` — read-only, no CSRF token needed.
- `GET /csrf-token` — returns the server's CSRF token (obtained from the `_csrf` request attribute).
- `POST /students` — requires a valid CSRF token. Without it, Spring Security returns **403 Forbidden**.

---

## SecurityConfig — Custom Filter Chain

`SecurityConfig.java` defines a custom `SecurityFilterChain` that replaces Spring Boot's auto-configuration:

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(customizer -> customizer.disable())
            .authorizeHttpRequests(request -> request.anyRequest().authenticated())
            .httpBasic(Customizer.withDefaults())
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider =
            new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        return provider;
    }
}
```

| What | Meaning |
|------|---------|
| `csrf().disable()` | CSRF protection turned off (useful for APIs) |
| `anyRequest().authenticated()` | Every endpoint requires login |
| `httpBasic()` | HTTP Basic authentication |
| `STATELESS` session | No `JSESSIONID` cookie — credentials sent on every request |
| `DaoAuthenticationProvider` | Looks up users from a database via `UserDetailsService` |
| `BCryptPasswordEncoder(12)` | Hashes passwords with BCrypt (strength 12) |

There's also a commented-out `InMemoryUserDetailsManager` example — an alternative that stores users in memory instead of a database.

---

## Database Authentication

Instead of the default in-memory user, this project authenticates against a **PostgreSQL** database using **Spring Data JPA**.

### Dependencies (pom.xml)

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <version>42.7.11</version>
</dependency>
```

### application.properties

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
spring.datasource.username=postgres
spring.datasource.password=postgres
```

### Users.java — JPA Entity

```java
@Entity
public class Users {
    @Id
    private int id;
    private String username;
    private String password;
    // getters and setters ...
}
```

### UserRepo.java — JPA Repository

```java
@Repository
public interface UserRepo extends JpaRepository<Users, Integer> {
    Users findByUsername(String username);
}
```

---

## Custom UserDetailsService

Spring Security needs a `UserDetailsService` to load users during authentication. `MyUserDetailsService` implements this interface and fetches users from the database:

```java
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        Users user = userRepo.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("user not found");
        }
        return new UserPrincipal(user);
    }
}
```

### UserPrincipal.java — UserDetails Adapter

Spring Security's authentication system works with the `UserDetails` interface. `UserPrincipal` adapts the `Users` entity to this interface:

```java
public class UserPrincipal implements UserDetails {

    private Users user;

    public UserPrincipal(Users user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("USER"));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    // isAccountNonExpired, isAccountNonLocked,
    // isCredentialsNonExpired, isEnabled → all return true
}
```

The flow is:
```
Request → SecurityFilterChain → DaoAuthenticationProvider
    → MyUserDetailsService.loadUserByUsername()
    → UserRepo.findByUsername()
    → UserPrincipal (wraps Users entity)
    → BCryptPasswordEncoder verifies password
```

---

## User Registration

Users can register via a `POST /register` endpoint. The password is hashed with BCrypt before being stored.

### UserController.java

```java
@RestController
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping("/register")
    public Users register(@RequestBody Users user) {
        return service.register(user);
    }
}
```

### UserService.java

```java
@Service
public class UserService {

    @Autowired
    private UserRepo repo;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public Users register(Users user) {
        user.setPassword(encoder.encode(user.getPassword()));
        repo.save(user);
        return user;
    }
}
```

### Testing with curl

```bash
# 1. Register a new user
curl -X POST http://localhost:8080/register \
  -H "Content-Type: application/json" \
  -d '{"id": 1, "username": "mateus", "password": "test"}'

# 2. Authenticate with the registered user
curl -u mateus:test http://localhost:8080/

# 3. Get CSRF token (if CSRF is enabled)
curl -u mateus:test http://localhost:8080/csrf-token

# 4. POST with CSRF token
curl -X POST http://localhost:8080/students \
  -u mateus:test \
  -H "Content-Type: application/json" \
  -H "X-CSRF-TOKEN: <token>" \
  -d '{"id": 3, "name": "Anna", "marks": 90}'
```
