# Spring Web App Demo Project

This project demonstrates how to build a web application using Spring Boot:

- Java 25
- Maven
- Spring Boot 4.0.6
- Spring Web MVC (REST API)
- Spring DevTools (Auto-restart, LiveReload)
- Lombok

## Project Structure (MVC Architecture)

```
src/main/java/com/mateuslopes/SpringWebApp/
├── SpringWebAppApplication.java   -- Entry point
├── controller/
│   ├── HomeController.java        -- "/", "/about"
│   ├── LoginController.java       -- "/login"
│   └── ProductController.java     -- "/products"
├── model/
│   └── Product.java               -- Product entity
└── service/
    └── ProductService.java        -- Business logic / data access
```

The application follows the **Model-View-Controller (MVC)** pattern:
- **Controller** — Handles HTTP requests and returns responses
- **Service** — Contains business logic, separate from request handling
- **Model** — Represents the data/entity structure

### Separation of Concerns

Business logic lives in the **service layer** rather than controllers. This keeps controllers lean and focused on request handling, making the code more maintainable and testable.

### Returning Structured Data (JSON)

Modern applications return structured data (JSON) instead of plain text or HTML. Controllers return entity data directly — Spring automatically serializes it to JSON.

```java
@RestController
public class ProductController {

    @Autowired
    ProductService service;

    @RequestMapping("/products")
    public List<Product> getProducts() {
        return service.getProducts();
    }
}
```

**Use cases:** Returning product lists, user data, or any structured information (e.g., products in an e-commerce application).

## Dependencies

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
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <version>1.18.42</version>
        <scope>compile</scope>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-webmvc-test</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
```

### Key Dependencies

| Dependency | Purpose |
|---|---|
| `spring-boot-starter-webmvc` | Core Spring MVC for REST APIs |
| `spring-boot-devtools` | Auto-restart, LiveReload during development |
| `lombok` | Reduces boilerplate code (`@Data`, `@AllArgsConstructor`) |
| `spring-boot-starter-webmvc-test` | Testing utilities for web MVC |

## Controllers

Controllers handle client requests. `@RestController` combines `@Controller` and `@ResponseBody` — methods return data (JSON/string) directly rather than view templates.

### HomeController

```java
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

### LoginController

```java
@RestController
public class LoginController {

    @RequestMapping("/login")
    public String login() {
        return "Login page!";
    }
}
```

### ProductController

```java
@RestController
public class ProductController {

    @Autowired
    ProductService service;

    @RequestMapping("/products")
    public List<Product> getProducts() {
        return service.getProducts();
    }
}
```

## Service Layer

The service layer encapsulates business logic and data retrieval, keeping controllers clean.

```java
@Service
public class ProductService {

    List<Product> products = Arrays.asList(
        new Product(1, "Iphone", 5000),
        new Product(2, "Camera", 7000),
        new Product(3, "Macbook", 10000)
    );

    public List<Product> getProducts() {
        return products;
    }
}
```

Currently returns **dummy data** for initial testing — no database involved yet.

## Model with Lombok

Lombok eliminates boilerplate: `@Data` generates getters, setters, `toString`, `equals`, `hashCode`; `@AllArgsConstructor` generates a constructor with all fields.

```java
@Data
@AllArgsConstructor
public class Product {
    private int prodId;
    private String prodName;
    private int price;
}
```

## Application Configuration

```properties
spring.application.name=SpringWebApp
```

Spring Boot auto-configuration handles the rest.

## Request Mapping Endpoints

| Endpoint | Controller | Returns |
|---|---|---|
| `GET /` | HomeController | `"Welcome to java web!"` |
| `GET /about` | HomeController | `"About page!"` |
| `GET /login` | LoginController | `"Login page!"` |
| `GET /products` | ProductController | JSON array of products |

## Key Takeaways

- **Request Mapping** — Mapping requests to specific endpoints is crucial for web applications.
- **Data Formats** — JSON is the preferred format for sending structured data between server and client.
- **Separation of Concerns** — A service layer keeps code clean by separating business logic from request handling.
- **Lombok** — Significantly reduces boilerplate, making the codebase cleaner and easier to manage.
- **MVC Pattern** — Always structure your application following MVC to enhance maintainability.

## Lessons Learned

- Understand the client-server model for web development.
- Familiarity with JSON and XML is important for data representation.
- Properly configuring a Spring Boot project is essential for leveraging its capabilities.
- Use tools like Postman for effective API testing.
- Systematic debugging and restarting can resolve many unexpected issues (e.g., port conflicts, Lombok issues).
- Spring's annotations simplify request handling and improve code readability.
- Spring Boot's auto-configuration eliminates boilerplate, letting you focus on business logic.
