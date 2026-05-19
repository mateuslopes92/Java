# Spring Web App Demo Project

This project demonstrates how to build a web application using Spring Boot:

- Java 25
- Maven
- Spring Boot 4.0.6
- Spring Web MVC (REST API)
- Spring DevTools (Auto-restart, LiveReload)
- Lombok
- Spring Data JPA
- H2 Database (in-memory)

## Project Structure (MVC Architecture + Repository Layer)

```
src/main/java/com/mateuslopes/SpringWebApp/
├── SpringWebAppApplication.java   -- Entry point
├── controller/
│   ├── HomeController.java        -- "/", "/about"
│   ├── LoginController.java       -- "/login"
│   └── ProductController.java     -- Products CRUD
├── model/
│   └── Product.java               -- Product entity
├── repository/
│   └── ProductRepository.java     -- Database access layer
└── service/
    └── ProductService.java        -- Business logic / data access
```

The application follows a layered architecture:

```
Client (Postman) → Controller → Service → Repository → Database
```

- **Controller** — Handles HTTP requests and returns responses
- **Service** — Contains business logic, separate from request handling
- **Repository** — Connects to the database, performs CRUD operations
- **Model** — Represents the data/entity structure mapped to a database table

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
| `spring-boot-starter-data-jpa` | Spring Data JPA for database interaction |
| `spring-boot-devtools` | Auto-restart, LiveReload during development |
| `lombok` | Reduces boilerplate code (`@Data`, `@AllArgsConstructor`) |
| `com.h2database:h2` | H2 in-memory database driver |
| `spring-boot-starter-webmvc-test` | Testing utilities for web MVC |

## Product API (CRUD Operations)

The `ProductController` exposes RESTful endpoints for managing products. It interacts with the `ProductService` which uses a **JPA repository** to persist data in the H2 database.

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

    @PutMapping("/products")
    public void updateProduct(@RequestBody Product product) {
        service.updateProduct(product);
    }

    @DeleteMapping("product/{prodId}")
    public void deleteProduct(@PathVariable int prodId) {
        service.deleteProduct(prodId);
    }
}
```

- `@PathVariable` extracts values from the URI path, enabling **dynamic endpoints** (e.g., `/product/1`, `/product/2`).
- `@RequestBody` binds the HTTP request body to a Java object — Spring automatically deserializes incoming JSON into the `Product` model.
- `@PutMapping` maps HTTP PUT requests — used for updating existing resources.
- `@DeleteMapping` maps HTTP DELETE requests — used for removing resources.

### ProductService

```java
@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Product getProductId(int prodId) {
        return productRepository.findById(prodId).orElse(new Product());
    }

    public void addProduct(Product product) {
        productRepository.save(product);
    }

    public void updateProduct(Product product) {
        productRepository.save(product);
    }

    public void deleteProduct(int prodId) {
        productRepository.deleteById(prodId);
    }
}
```

The service delegates all data access to the repository — no manual list management, no SQL boilerplate. Spring Data JPA generates the queries at runtime.

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

## Product Entity with JPA & Lombok

`Product` is a JPA entity mapped to the `product` database table:

```java
@Data
@NoArgsConstructor
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int prodId;
    private String prodName;
    private int price;

}
```

- **`@Entity`** — Marks the class as a database entity mapped to a table.
- **`@Id`** — Defines the primary key.
- **`@GeneratedValue(strategy = GenerationType.IDENTITY)`** — Auto-increments the ID on insert.
- **`@Data`** (Lombok) — Generates getters, setters, `toString`, `equals`, `hashCode`.
- **`@NoArgsConstructor`** (Lombok) — Required no-arg constructor for JPA.

Without getters/setters, Jackson cannot deserialize incoming JSON into the object, causing all fields to save as defaults (0, null).

## Spring Data JPA

### Why a Database?

The service layer currently holds **hardcoded data** in an in-memory list. This data is lost on restart and cannot scale. Real applications persist data in a **database**.

### Java Database Connectivity (JDBC)

**JDBC** is the traditional Java API for database operations. It requires:
1. Loading a driver
2. Opening a connection
3. Writing raw SQL queries
4. Processing `ResultSet` manually
5. Handling connections, statements, result sets

**Spring JDBC** simplifies this with `JdbcTemplate`, reducing boilerplate.

### Object-Relational Mapping (ORM)

**ORM** maps Java objects to database tables and vice versa:

```
Product object (Java)  ←→  products table (Database)
```

The **Java Persistence API (JPA)** is a standard specification for ORM. **Hibernate** is the most popular JPA implementation. Using JPA standardizes your code — you can switch ORM providers without changing your code.

### Spring Data JPA

**Spring Data JPA** builds on top of JPA and Hibernate, further simplifying data access. You define a **repository interface** and Spring provides the implementation at runtime — no manual coding needed.

### Dependencies

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>

<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
</dependency>
```

### H2 Database Configuration

**H2** is an in-memory database, ideal for development and testing. Configure it in `application.properties`:

```properties
spring.application.name=SpringWebApp

spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

The **H2 console** is accessible at `/h2-console` in the browser, allowing you to inspect tables and run queries during development.

### Repository Layer

The repository separates data access logic from service/business logic:

```java
@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
}
```

By extending `JpaRepository`, the interface inherits CRUD methods (`findAll`, `findById`, `save`, `deleteById`) — Spring Data JPA generates the SQL queries automatically at runtime.

### H2 Console Servlet Registration

Spring Boot 4.0.6 does not auto-register the H2 console servlet. A `WebConfig` class explicitly registers it using the Jakarta-compatible servlet:

```java
@Configuration
public class WebConfig {

    @Bean
    ServletRegistrationBean<JakartaWebServlet> h2ConsoleServletRegistration() {
        ServletRegistrationBean<JakartaWebServlet> registrationBean =
                new ServletRegistrationBean<>(new JakartaWebServlet(), "/h2-console/*");
        registrationBean.setLoadOnStartup(1);
        return registrationBean;
    }
}
```

Without this explicit registration, accessing `/h2-console` returns a 404.

## Application Configuration

```properties
spring.application.name=SpringWebApp
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.h2.console.enabled=true
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
| PUT | `/products` | ProductController | Update an existing product |
| DELETE | `/product/{prodId}` | ProductController | Delete a product by ID |

## HTTP Methods (CRUD Operations)

| Method | Operation | Description |
|--------|-----------|-------------|
| **GET** | Read | Fetch one or more resources |
| **POST** | Create | Add a new resource |
| **PUT** | Update | Replace an existing resource |
| **DELETE** | Delete | Remove a resource |

These four operations make up **CRUD** — the foundation of most RESTful APIs.

## Testing with Postman

Use Postman to interact with the API (all requests send `Content-Type: application/json`):

- **GET /products** — Returns the full product list as a JSON array.
- **GET /product/1** — Returns the product with ID 1 (404 if not found).
- **POST /product** — Send a JSON body like `{"prodName": "Mouse", "price": 100}` to add a product. Do **not** include `prodId` — it is auto-generated by the database. No response body on success.
- **PUT /products** — Send a JSON body with all fields, e.g., `{"prodId": 1, "prodName": "Macbook Pro", "price": 15000}`. Replaces the existing product.
- **DELETE /product/1** — Deletes the product with ID 1 from the database.

Pay attention to **HTTP status codes** in responses (200 for success, 404 for not found, etc.) — they are crucial for diagnosing API issues.

## Key Takeaways

- **Repository Pattern** — The repository layer is crucial for data management and should be separated from the service/business logic layer.
- **Spring JPA Magic** — Spring Data JPA simplifies database interactions by automatically generating SQL queries from repository method names.
- **Entity Annotations** — Proper use of annotations like `@Entity`, `@Id`, and `@GeneratedValue` is essential for defining database tables and primary keys.
- **CRUD Operations** — Basic CRUD operations can be performed with minimal code using `findAll`, `findById`, `save`, and `deleteById` from `JpaRepository`.
- **Get/Set Methods** — JPA entities need getters and setters (via Lombok `@Data` or manually) for Jackson to deserialize JSON request bodies correctly. Without them, fields default to 0/null.
- **Auto-Generated IDs** — Use `@GeneratedValue` so the database handles ID assignment — the client should not send an ID when creating resources.
- **H2 Console** — Spring Boot 4.0.6 no longer auto-registers the H2 console servlet; it must be registered manually with a `@Configuration` class.
- **Jakarta Servlet** — Modern Spring Boot uses Jakarta Servlet 5+; register `JakartaWebServlet` instead of the legacy `WebServlet`.
- **Structured data** (JSON) is the standard format for server-client data interchange.
- **Separation of Concerns** — A service layer keeps code clean by separating business logic from request handling.
- **Lombok** — Significantly reduces boilerplate, making the codebase cleaner and easier to manage.
- **Dynamic endpoints** with path variables allow flexible and reusable API design.

## Lessons Learned

- Properly configuring a Spring Boot project is essential for leveraging its capabilities.
- Use tools like Postman for effective API testing and debugging.
- Systematic debugging and restarting can resolve many unexpected issues (e.g., port conflicts, Lombok issues, unsupported media types, missing servlet registration).
- Familiarity with converting between JSON and Java objects is important for data interchange in APIs — Jackson relies on setters to populate entity fields.
- Spring Boot's auto-configuration eliminates boilerplate, but not all features are auto-configured in every version — verify which auto-configurations are available.
- Structuring your application following MVC + Repository pattern enhances maintainability and testability.
- Real-world applications need a database (JPA + H2) instead of in-memory lists for persistent, scalable data management.
- Always verify that the correct dependencies are included in your project (e.g., JPA, H2 driver) and that their scopes allow compile-time access when referencing classes in code.
- Understand how Java objects map to database tables — it is fundamental for effective data management with ORM.
- Errors during setup (missing URL, driver not found, servlet not registered) are valuable learning opportunities for debugging configuration issues.
