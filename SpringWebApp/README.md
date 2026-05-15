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

    public void updateProduct(Product product) {
        int index = 0;
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getProdId() == product.getProdId()) {
                index = i;
            }
        }
        products.set(index, product);
    }

    public void deleteProduct(int prodId) {
        int index = 0;
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getProdId() == prodId) {
                index = i;
            }
        }
        products.remove(index);
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

Use Postman to interact with the API:

- **GET /products** — Returns the full product list as a JSON array.
- **GET /product/1** — Returns the product with ID 1 (404 if not found).
- **POST /product** — Send a JSON body like `{"prodId": 4, "prodName": "Mouse", "price": 100}` to add a product. No response body on success.
- **PUT /products** — Send a JSON body with updated fields, e.g., `{"prodId": 3, "prodName": "Macbook Pro", "price": 15000}`. Replaces the product at the matching index.
- **DELETE /product/3** — Deletes the product with ID 3 from the list.

Pay attention to **HTTP status codes** in responses (200 for success, 404 for not found, etc.) — they are crucial for diagnosing API issues.

## Key Takeaways

- **CRUD Operations** — Implement Create, Read, Update, and Delete operations in a Spring Boot application.
- **HTTP Methods** — GET (read), POST (create), PUT (update), DELETE (delete) each map to specific operations in RESTful APIs.
- **Structured data** (JSON) is the standard format for server-client data interchange.
- **Separation of Concerns** — A service layer keeps code clean by separating business logic from request handling.
- **Lombok** — Significantly reduces boilerplate, making the codebase cleaner and easier to manage.
- **Dynamic endpoints** with path variables allow flexible and reusable API design.
- **Understanding status codes** is crucial for diagnosing issues with API requests.
- **Debugging** — Use tools like Postman to test and debug API endpoints (e.g., unsupported media types, method not allowed errors).

## Lessons Learned

- Properly configuring a Spring Boot project is essential for leveraging its capabilities.
- Use tools like Postman for effective API testing and debugging.
- Systematic debugging and restarting can resolve many unexpected issues (e.g., port conflicts, Lombok issues, unsupported media types).
- Familiarity with converting between JSON and Java objects is important for data interchange in APIs.
- Spring Boot's auto-configuration eliminates boilerplate, letting you focus on business logic.
- Structuring your application following MVC enhances maintainability.
- Manual data management (in-memory lists) has limitations — real-world applications need a database (e.g., Spring Data JPA).
- Following the **DRY principle** avoids code repetition and keeps the codebase clean.
