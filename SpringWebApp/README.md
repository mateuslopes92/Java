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
│   └── ProductController.java     -- Products CRUD
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

## Product API (CRUD Operations)

The `ProductController` exposes RESTful endpoints for managing products. It interacts with the `ProductService` which currently holds an **in-memory list** (no database yet).

### ProductController

```java
@RestController
public class ProductController {

    @Autowired
    ProductService service;

    @GetMapping("/products")
    public List<Product> getProducts() {
        return service.getProducts();
    }

    @GetMapping("product/{prodId}")
    public Product getProductById(@PathVariable int prodId) {
        return service.getProductId(prodId);
    }

    @PostMapping("product")
    public void addProduct(@RequestBody Product product) {
        service.addProduct(product);
    }
}
```

- `@PathVariable` extracts values from the URI path, enabling **dynamic endpoints** (e.g., `/product/1`, `/product/2`).
- `@RequestBody` binds the HTTP request body to a Java object — Spring automatically deserializes incoming JSON into the `Product` model.

### ProductService

```java
@Service
public class ProductService {

    List<Product> products = new ArrayList<>(Arrays.asList(
        new Product(1, "Iphone", 5000),
        new Product(2, "Camera", 7000),
        new Product(3, "Macbook", 10000)
    ));

    public List<Product> getProducts() {
        return products;
    }

    public Product getProductId(int prodId) {
        return products
                .stream()
                .filter(p -> p.getProdId() == prodId)
                .findFirst()
                .get();
    }

    public void addProduct(Product product) {
        products.add(product);
    }
}
```

The list is wrapped in `new ArrayList<>()` to keep it **mutable** — newly added products persist at runtime.

## Other Controllers

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

## API Endpoints

| Method | Endpoint | Controller | Description |
|--------|----------|------------|-------------|
| GET | `/` | HomeController | Welcome message |
| GET | `/about` | HomeController | About page |
| GET | `/login` | LoginController | Login page |
| GET | `/products` | ProductController | List all products |
| GET | `/product/{prodId}` | ProductController | Get product by ID |
| POST | `/product` | ProductController | Add a new product |

## Testing with Postman

Use Postman to interact with the API:

- **GET /products** — Returns the full product list as a JSON array.
- **GET /product/1** — Returns the product with ID 1 (404 if not found).
- **POST /product** — Send a JSON body like `{"prodId": 4, "prodName": "Mouse", "price": 100}` to add a product. No response body on success.

Pay attention to **HTTP status codes** in responses (200 for success, 404 for not found, etc.) — they are crucial for diagnosing API issues.

## Key Takeaways

- **REST APIs** use endpoints to perform operations on resources like products.
- **Structured data** (JSON) is the standard format for server-client data interchange.
- **Separation of Concerns** — A service layer keeps code clean by separating business logic from request handling.
- **Lombok** — Significantly reduces boilerplate, making the codebase cleaner and easier to manage.
- **Dynamic endpoints** with path variables allow flexible and reusable API design.
- **Understanding status codes** is crucial for diagnosing issues with API requests.

## Lessons Learned

- Properly configuring a Spring Boot project is essential for leveraging its capabilities.
- Use tools like Postman for effective API testing and debugging.
- Systematic debugging and restarting can resolve many unexpected issues (e.g., port conflicts, Lombok issues).
- Familiarity with converting between JSON and Java objects is important for data interchange in APIs.
- Spring Boot's auto-configuration eliminates boilerplate, letting you focus on business logic.
- Structuring your application following MVC enhances maintainability.
