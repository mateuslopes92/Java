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

### Greeting Endpoint (Smoke Test — Replaced)

The initial greeting endpoint at `GET /api/` was replaced once the full API was built out. The `/api/products` endpoint now serves as the primary entry point, returning the full product list wrapped in a `ResponseEntity` with proper HTTP status codes.

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

    public Product getProductById(int id) {
        return productRepository.findById(id).orElse(null);
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
| `id` | `Integer` | Primary key, auto-increment |
| `name` | `String` | Product name |
| `desc` | `String` | Product description |
| `brand` | `String` | Brand name |
| `price` | `BigDecimal` | Product price |
| `category` | `String` | Product category |
| `releaseDate` | `Date` | Release date |
| `available` | `boolean` | Availability status |
| `quantity` | `Integer` | Stock quantity |
| `imageName` | `String` | Original filename of the uploaded image |
| `imageType` | `String` | MIME type of the image (e.g. `image/jpeg`, `image/png`) |
| `imageData` | `byte[]` (`@Lob`) | Binary image data stored in the database |

```java
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String desc;
    private String brand;
    private BigDecimal price;
    private String category;

    private Date releaseDate;
    private boolean available;
    private Integer quantity;

    private String imageName;
    private String imageType;
    @Lob
    private byte[] imageData;
}
```

## API Endpoints

All endpoints are prefixed with `/api`. The controller is annotated with `@CrossOrigin` to allow cross-origin requests from the frontend (`ecom-frontend-1`), which runs on a different port.

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/products` | List all products |
| GET | `/api/product/{id}` | Get a single product by ID |
| POST | `/api/product` | Add a new product with an image (multipart/form-data) |
| GET | `/api/product/{productId}/image` | Retrieve the image binary for a specific product |
| PUT | `/api/product/{id}` | Update an existing product with image (multipart/form-data) |
| DELETE | `/api/product/{id}` | Delete a product by ID |

### ResponseEntity & HTTP Status Codes

The controller uses `ResponseEntity` to wrap responses with proper HTTP status codes:

```java
@RequestMapping("/products")
public ResponseEntity<List<Product>> getAllProducts(){
    return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
}

@GetMapping("/product/{id}")
public ResponseEntity<Product> getProduct(@PathVariable int id){
    Product product = productService.getProductById(id);

    if(product != null)
        return new ResponseEntity<>(product, HttpStatus.OK);
    else
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
}
```

- `200 OK` — returned when the requested product exists
- `404 Not Found` — returned when no product matches the given ID
- Using `ResponseEntity` gives explicit control over the response status, headers, and body

### Adding Products with Images

Products with images are created via a `POST /api/product` request using `multipart/form-data`:

```java
@PostMapping("/product")
public ResponseEntity<?> addProduct(@RequestPart Product product, @RequestPart MultipartFile imageFile){
    try {
        Product productCreated = productService.addProduct(product, imageFile);
        return new ResponseEntity<>(productCreated, HttpStatus.CREATED);
    } catch (Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
```

- `@RequestPart Product product` — deserializes the product JSON from the multipart request
- `@RequestPart MultipartFile imageFile` — captures the uploaded image file
- Returns `201 Created` on success, `500 Internal Server Error` on failure

The service layer extracts image metadata from the `MultipartFile`:

```java
public Product addProduct(Product product, MultipartFile imageFile) throws IOException {
    product.setImageName(imageFile.getOriginalFilename());
    product.setImageType(imageFile.getContentType());
    product.setImageData(imageFile.getBytes());
    return productRepository.save(product);
}
```

- `imageName` — original filename from the client
- `imageType` — MIME type (e.g. `image/jpeg`) for setting the correct `Content-Type` header when serving
- `imageData` — raw byte array stored as a `@Lob` (Large Object) in the database

### Image Retrieval Endpoint

Images are served through a dedicated endpoint that returns the raw bytes with the correct content type:

```java
@GetMapping("product/{productId}/image")
public ResponseEntity<byte[]> getImageByProductId(@PathVariable int productId){
    Product product = productService.getProductById(productId);
    byte[] imageFile = product.getImageData();

    return ResponseEntity.ok()
            .contentType(MediaType.valueOf(product.getImageType()))
            .body(imageFile);
}
```

- Fetches the product by ID, then extracts the stored byte array and MIME type
- Returns the image with the correct `Content-Type` header so browsers render it properly
- The frontend requests this endpoint with `responseType: "blob"` and creates an object URL for display

### Updating a Product

Products are updated via `PUT /api/product/{id}` using `multipart/form-data` (product JSON + image):

```java
@PutMapping("/product/{id}")
public ResponseEntity<String> updateProduct(@PathVariable int id, @RequestPart Product product, @RequestPart MultipartFile imageFile){
    Product productUpdated = null;
    try {
        productUpdated = productService.updateProduct(id, product, imageFile);
    } catch (IOException e) {
        return new ResponseEntity<>("Failed to update", HttpStatus.BAD_REQUEST);
    }

    if(productUpdated != null){
        return new ResponseEntity<>("Updated", HttpStatus.OK);
    } else {
        return new ResponseEntity<>("Failed to update", HttpStatus.BAD_REQUEST);
    }
}
```

The service layer updates the product data along with the new image:

```java
public Product updateProduct(int id, Product product, MultipartFile imageFile) throws IOException {
    product.setImageData(imageFile.getBytes());
    product.setImageName(imageFile.getName());
    product.setImageType(imageFile.getContentType());
    return productRepository.save(product);
}
```

- The frontend pre-fills an update form by fetching existing product data and its image
- Sends the modified product JSON and new image file as multipart/form-data
- Returns `200 OK` on success, `400 Bad Request` on failure

### Deleting a Product

Products are deleted via `DELETE /api/product/{id}`:

```java
@DeleteMapping("product/{id}")
public ResponseEntity<String> deleteProduct(@PathVariable int id){
    Product product = productService.getProductById(id);

    if(product != null){
        productService.deleteProduct(id);
        return new ResponseEntity<>("Product deleted!", HttpStatus.OK);
    } else {
        return new ResponseEntity<>("Product not found!", HttpStatus.NOT_FOUND);
    }
}
```

The service layer delegates deletion to the repository:

```java
public void deleteProduct(int id) {
    productRepository.deleteById(id);
}
```

- The frontend triggers deletion from the Product detail page via a "Delete" button
- The product is removed from the cart (via context) and the page navigates back to the home screen
- Returns `200 OK` when found and deleted, `404 Not Found` if the product doesn't exist

### Date Formatting with Jackson

The `releaseDate` field uses `@JsonFormat` to control how dates are serialized to JSON:

```java
@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
private Date releaseDate;
```

Without this annotation, Jackson would serialize `Date` as a timestamp (milliseconds since epoch). With `@JsonFormat`, the date is sent as a human-readable string (e.g., `"01-01-2025"`).

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
- **RESTful Services** — Implementing endpoints with `ResponseEntity` and proper HTTP status codes ensures effective communication between frontend and backend.
- **CRUD Operations** — The API now supports full CRUD: Create (POST), Read (GET), Update (PUT), and Delete (DELETE) for products.
- **Multipart Updates** — The update endpoint (`PUT /api/product/{id}`) uses `@RequestPart` to receive both product JSON and image file, mirroring the create flow.
- **Delete with Safety Checks** — The delete endpoint verifies the product exists before attempting deletion, returning `404 Not Found` for invalid IDs.
- **Initial Data** — Use `data.sql` with `defer-datasource-initialization=true` to seed default data in H2 without schema conflicts.
- **Testing Endpoints** — Regularly test API endpoints using Postman to ensure they return expected results.
- **Product Detail View** — The frontend now allows users to click on individual products to view more details via a new Product component at `/product/{id}`.
- **Backend Product Lookup** — A new service method (`getProductById`) and controller endpoint (`GET /api/product/{id}`) handle fetching a single product by its ID.
- **Null Safety** — The service layer returns `null` when a product is not found, and the controller responds with `404 Not Found`.
- **Date Formatting** — Jackson's `@JsonFormat` annotation formats the `releaseDate` field as a readable string (`dd-MM-yyyy`) instead of a raw timestamp.
- **Image Storage** — Images are stored directly in the database as `byte[]` with `@Lob`, keeping the data fully contained without external file system dependencies.
- **Multipart Handling** — Using `@RequestPart` with `MultipartFile` allows receiving both JSON and binary file data in a single request.
- **Image Serving** — A dedicated image endpoint (`GET /api/product/{id}/image`) returns raw bytes with the correct `MediaType`, enabling the frontend to render images via blob URLs.
- **UI Integration** — The frontend fetches images per-product after loading the product list, creating a seamless experience where product cards display thumbnails and detail pages show full images.
- **Update Product Page** — A new `UpdateProduct` component at `/product/update/:id` pre-fills a form with existing product data and image, allowing users to modify and submit changes.
- **Delete from Detail Page** — The Product detail page includes a functional Delete button that removes the product, clears it from the cart context, and navigates back to the home screen.
- **Frontend-Backend Sync** — After delete, the frontend refreshes the product list via context (`refreshData`) to keep the UI in sync with the backend.

## Lessons Learned

- **Best Practices** — Use constructor injection over field injection for better code maintainability and testability.
- **Database Management** — Ensure the database schema is created before attempting to insert data. `defer-datasource-initialization=true` ensures data.sql runs after Hibernate DDL.
- **Data Persistence** — H2 is temporary — data resets on every restart. Use `data.sql` for seed data during development.
- **Testing** — Regularly test API endpoints using tools like Postman to verify expected results.
- **Data Formatting** — Jackson's `@JsonFormat` provides clean, human-readable date serialization. Always consider the client-side experience when formatting dates.
- **Frontend-Backend Integration** — Connecting frontend requests (`/product/{id}`) to backend services is essential for dynamic, data-driven applications.
- **Error Handling** — Always check for null values and handle missing resources gracefully. Returning `404 Not Found` for missing products improves user experience and client-side logic.
- **HTTP Status Codes** — Using the correct status codes (`200 OK`, `404 Not Found`, `201 Created`, `500 Internal Server Error`) helps manage client-side logic effectively, allowing for better error handling and user feedback.
- **Iterative Development** — The development process is continuous; always look for ways to enhance functionality and user experience in future updates.
- **Image Handling Complexity** — Managing image uploads introduces additional complexity: ensuring correct format and size, handling security concerns, and storing/retrieving binary data efficiently.
- **Importance of Images in E-Commerce** — Including product images significantly enhances the user experience on e-commerce platforms, making the UI more informative and engaging.
- **Frontend-Backend Integration for Images** — Sending product and image data from the frontend to the backend requires careful coordination of multipart requests (`FormData` + `Blob` on the client, `@RequestPart` on the server).
- **Incremental Feature Building** — Building and testing the image feature incrementally (model first, then service, then controller, then frontend) helped identify and resolve issues early in the development process.
- **Update API Design** — The update endpoint requires careful handling of product data alongside image files. Using `@RequestPart` for both JSON and `MultipartFile` keeps the contract consistent with the create endpoint.
- **Delete API Design** — Deletion is straightforward: only the product ID is needed. Always verify existence before deleting and return appropriate status codes (`200 OK` vs `404 Not Found`).
- **Testing Features End-to-End** — After implementing update and delete, testing the full flow (add → update → delete) is essential to ensure all features work correctly together.
- **User Feedback** — The frontend provides feedback via alerts on success/failure for update and delete operations, which is important for user experience.
- **Code Organization** — Keeping the service layer modular (`addProduct`, `updateProduct`, `deleteProduct` as separate methods) makes the code easier to maintain and extend.

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
- **React Context API** is used to share global state (product list, cart) across components

### Product Detail Page

The frontend includes a `Product` component at `/product/:id` that fetches and displays a single product's details:

- Product image fetched from `GET /api/product/{id}/image` as a blob and rendered via an object URL
- Product category, name, brand, and description
- Price with an "Add to Cart" button
- Stock availability and quantity
- Release date (formatted as `dd-MM-yyyy`)
- **Update button** — navigates to `/product/update/{id}` for editing
- **Delete button** — deletes the product, removes it from cart, and navigates back to home

Each product card on the Home page is wrapped in a `<Link>` that navigates to `/product/{id}` when clicked.

### Update Product Page

The `UpdateProduct` component at `/product/update/:id` provides a form pre-filled with the existing product data:

- Fetches the current product via `GET /api/product/{id}` and its image via `GET /api/product/{id}/image`
- Pre-populates all fields: name, brand, description, price, category, stock quantity, availability, and image preview
- On submit, sends a `PUT /api/product/{id}` request with `multipart/form-data` containing the updated product JSON and a new image file
- Displays success/failure alerts based on the response

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
