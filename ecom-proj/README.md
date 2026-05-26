# E-Commerce Backend (ecom-proj)

Backend REST API for an e-commerce application built with Spring Boot. Consumed by the frontend at [`ecom-frontend-1`](../ecom-frontend-1/) (React + Vite).

- Java 25
- Maven
- Spring Boot 4.0.6
- Spring Web MVC (REST API)
- Spring DevTools (Auto-restart, LiveReload)
- Lombok
- Spring Data JPA
- H2 Database (in-memory)

## Project Setup

The project was created via [Spring Initializr](https://start.spring.io/) with the following dependencies:

- **Spring Web MVC** — for building REST APIs
- **Spring Data JPA** — for database interaction
- **H2 Database** — lightweight in-memory database for development
- **Lombok** — reduces boilerplate code
- **Spring DevTools** — auto-restart and LiveReload during development

### Database Configuration (H2)

H2 is an in-memory database ideal for development and testing — no installation needed, data resets on restart. Configured in `application.properties`:

```properties
spring.datasource.url=jdbc:h2:mem:ecom
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password

spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=update
spring.jpa.defer-datasource-initialization=true
```

- `ddl-auto=update` — Hibernate automatically creates/updates tables based on entity definitions
- `show-sql=true` — logs generated SQL queries for debugging
- H2 console available at `/h2-console` to inspect tables and run queries

### Greeting Endpoint (Smoke Test)

A simple endpoint was created to verify the server is running before building out the full API:

```java
@RestController
@RequestMapping("/api")
public class ProductController {

    @RequestMapping("/")
    public String greet() {
        return "Hello";
    }
}
```

Always test endpoints after setup to ensure they function as expected before moving to more complex features.

## Project Structure

```
src/main/java/com/mateuslopes92/ecom_proj/
├── EcomProjApplication.java   -- Entry point
├── WebConfig.java             -- H2 console servlet registration
├── controller/
│   └── ProductController.java -- REST API endpoints under /api
├── model/
│   └── Product.java           -- Product JPA entity
├── repo/
│   └── ProductRepository.java -- Database access layer
└── service/
    └── ProductService.java    -- Business logic
```

Layered architecture:

```
Client (React/Postman) → Controller → Service → Repository → Database
```

Each layer has a distinct responsibility:

- **Controller** — Handles HTTP requests, maps endpoints, and returns responses. Kept lean — no business logic.
- **Service** — Contains business logic, orchestrates data flow between controller and repository.
- **Repository** — Data access layer. Extends `JpaRepository` to inherit CRUD methods (`findAll`, `findById`, `save`, `deleteById`). Spring Data JPA generates SQL queries at runtime.
- **Model** — JPA entity mapped to a database table.

The frontend at `ecom-frontend-1` communicates with this backend via HTTP requests to the REST API.

### Service Layer

`ProductService` encapsulates business logic and mediates between the controller and the repository:

```java
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}
```

> **Best practice:** Constructor injection is preferred over field injection (`@Autowired`) — it makes dependencies explicit, improves testability, and ensures immutability.

### Repository Layer

`ProductRepository` extends `JpaRepository<Product, Integer>` — Spring Data JPA provides the implementation at runtime:

```java
@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
}
```

No manual SQL or implementation code needed. The interface inherits `findAll`, `findById`, `save`, `deleteById`, and more.

## Initial Data (data.sql)

Since H2 is an in-memory database, all data is lost on restart. To load default data on startup, create `src/main/resources/data.sql`:

```sql
INSERT INTO product (name, desc, brand, price, category, release_date, available, quantity)
VALUES ('Product A', 'Description A', 'Brand X', 29.99, 'Category 1', '2025-01-01', true, 10);

INSERT INTO product (name, desc, brand, price, category, release_date, available, quantity)
VALUES ('Product B', 'Description B', 'Brand Y', 49.99, 'Category 2', '2025-02-15', true, 25);
```

With `spring.jpa.defer-datasource-initialization=true`, the SQL runs after Hibernate creates the tables — avoiding schema conflicts.

## Product Entity

The `Product` entity is mapped to a database table with the following fields:

| Field | Type | Description |
|---|---|---|
| `id` | `int` | Primary key, auto-increment |
| `name` | `String` | Product name |
| `desc` | `String` | Product description |
| `brand` | `String` | Brand name |
| `price` | `BigDecimal` | Product price |
| `category` | `String` | Product category |
| `releaseDate` | `Date` | Release date |
| `available` | `boolean` | Availability status |
| `quantity` | `int` | Stock quantity |

```java
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String desc;
    private String brand;
    private BigDecimal price;
    private String category;
    private Date releaseDate;
    private boolean available;
    private int quantity;
}
```

## API Endpoints

All endpoints are prefixed with `/api`. The controller is annotated with `@CrossOrigin` to allow cross-origin requests from the frontend (`ecom-frontend-1`), which runs on a different port.

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/` | Welcome message |
| GET | `/api/products` | List all products |

## Dependencies

```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>4.0.6</version>
</parent>
```

| Dependency | Purpose |
|---|---|
| `spring-boot-starter-webmvc` | Core Spring MVC for REST APIs |
| `spring-boot-starter-data-jpa` | Spring Data JPA for database interaction |
| `spring-boot-devtools` | Auto-restart, LiveReload during development |
| `lombok` | Reduces boilerplate code (`@Data`, `@AllArgsConstructor`, `@NoArgsConstructor`) |
| `com.h2database:h2` | H2 in-memory database driver |
| `spring-boot-h2console` | H2 console servlet registration for Spring Boot 4.x |
| `spring-boot-starter-webmvc-test` | Testing utilities for web MVC |
| `spring-boot-starter-data-jpa-test` | Testing utilities for JPA |

## Key Takeaways

- **Layered Architecture** — Clear separation of concerns: Controller (HTTP handling) → Service (business logic) → Repository (data access) → Database.
- **Constructor Injection** — Prefer constructor injection over field injection (`@Autowired`) for better testability, explicit dependencies, and immutability.
- **Spring Data JPA** — Extending `JpaRepository` gives you CRUD methods automatically. No manual SQL or implementation code needed.
- **Database Configuration** — Configuring the database connection and JPA settings correctly saves debugging time later. H2 is ideal for development — no installation, data resets on restart.
- **Model Representation** — Defining a clear entity model with JPA annotations is essential for managing application data. Using `ddl-auto=update` lets Hibernate create tables automatically.
- **Lombok** — Significantly reduces boilerplate code (`@Data`, `@AllArgsConstructor`, `@NoArgsConstructor`), keeping the codebase clean and maintainable.
- **RESTful Services** — Implementing a basic REST controller early helps verify the server is working before building more complex features.
- **Initial Data** — Use `data.sql` with `defer-datasource-initialization=true` to seed default data in H2 without schema conflicts.
- **Testing Endpoints** — Regularly test API endpoints using Postman to ensure they return expected results.

## Lessons Learned

- **Best Practices** — Use constructor injection over field injection for better code maintainability and testability.
- **Database Management** — Ensure the database schema is created before attempting to insert data. `defer-datasource-initialization=true` ensures data.sql runs after Hibernate DDL.
- **Data Persistence** — H2 is temporary — data resets on every restart. Use `data.sql` for seed data during development.
- **Testing** — Regularly test API endpoints using tools like Postman to verify expected results.
- **Data Formatting** — Consider user-friendly formats for data presentation, especially dates.

## Related Frontend

The frontend for this API is in [`ecom-frontend-1`](../ecom-frontend-1/), a React + Vite application.

### Frontend Architecture

- **Single Page Application (SPA)** — The page does not reload on navigation; content updates dynamically. `index.html` is the single entry point.
- **Component-Based Architecture** — The UI is built from reusable components (like building blocks). Each component encapsulates its own structure, logic, and styles.
- **JSX** — A syntax extension that allows writing HTML-like tags in JavaScript. Custom tags map directly to React components.

### Client Side

The client can be:
- The React application (primary)
- API testing tools like **Postman** or **Swagger**

The front-end and back-end are fully separated and communicate exclusively via REST APIs.

### Data Handling

- Data is fetched from the backend API using HTTP requests (via **Axios**)
- State management with React hooks like `useState` handles the data within components
- Components re-render automatically when state changes

### Key Dependencies

| Dependency | Purpose |
|---|---|
| **Axios** | HTTP client for API requests to this backend |
| **React Router** | Client-side routing for SPA navigation |
| **Bootstrap** | UI component library and styling |
| **Sass** | CSS pre-processor for styling |

### Running the Frontend

```bash
cd ecom-frontend-1
npm install
npm run dev
```

The frontend dev server typically runs on `http://localhost:5173` and proxies API requests to this backend.

## Running the Application

```bash
./mvnw spring-boot:run
```

The API will be available at `http://localhost:8080/api/`.
