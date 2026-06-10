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

To test with `curl`:
```bash
# 1. Authenticate and get a session cookie + CSRF token
curl -v -u user:password http://localhost:8080/csrf-token

# 2. Use the CSRF token from the response to POST a new student
curl -X POST http://localhost:8080/students \
  -u user:password \
  -H "Content-Type: application/json" \
  -H "X-CSRF-TOKEN: <token>" \
  -d '{"id": 3, "name": "Anna", "marks": 90}'
```
