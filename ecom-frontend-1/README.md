# E-Commerce Frontend (ecom-frontend-1)

Frontend React application for an e-commerce platform. Consumes the REST API at [`ecom-proj`](../ecom-proj/) (Spring Boot backend).

- React 19
- Vite
- React Router
- Bootstrap
- Axios
- Sass

## Project Setup

```bash
npm install
npm run dev
```

The dev server runs on `http://localhost:5173` and communicates with the backend at `http://localhost:8080`.

## Project Structure

```
src/
├── App.jsx                  -- Root component with routes
├── App.css                  -- Global styles
├── axios.jsx                -- Axios instance with base URL config
├── main.jsx                 -- Entry point
├── Context/
│   └── Context.jsx          -- Global state (products, cart) via React Context
└── components/
    ├── Home.jsx             -- Product listing with image thumbnails
    ├── Product.jsx          -- Single product detail page
    ├── AddProduct.jsx       -- Product creation form with image upload
    ├── Cart.jsx             -- Shopping cart page
    ├── CheckoutPopup.jsx    -- Checkout confirmation modal
    └── Navbar.jsx           -- Navigation with search and category filter
```

## Route Map

| Route          | Component  | Description                          |
| -------------- | ---------- | ------------------------------------ |
| `/`            | Home       | Product catalog with image cards     |
| `/product/:id` | Product    | Single product detail with image     |
| `/add_product` | AddProduct | Form to add a new product with image |
| `/cart`        | Cart       | Shopping cart with checkout          |

## Adding Products with Images

The `AddProduct` component allows admin users to create products with images in a single request.

### Data Flow

1. **Form state** — Product fields (`name`, `brand`, `desc`, `price`, `category`, `quantity`, `releaseDate`, `available`) and a separate `image` file state.

2. **Image selection** — `handleImageChange` captures the selected file from the file input.

3. **Form submission** — `submitHandler` builds a `FormData` object:
   - Appends the image file as `"imageFile"`
   - Serializes the product object as a JSON `Blob` under `"product"`
   - Sends a `POST` request to `http://localhost:8080/api/product` with `Content-Type: multipart/form-data`

4. **Backend processing** — The Spring Boot controller uses `@RequestPart` to deserialize both the product JSON and the `MultipartFile`, storing the image as bytes in the database.

### Code Example

```jsx
const submitHandler = (event) => {
  event.preventDefault();
  const formData = new FormData();
  formData.append("imageFile", image);
  formData.append(
    "product",
    new Blob([JSON.stringify(product)], { type: "application/json" })
  );

  axios
    .post("http://localhost:8080/api/product", formData, {
      headers: { "Content-Type": "multipart/form-data" },
    })
    .then((response) => {
      alert("Product added successfully");
    })
    .catch((error) => {
      alert("Error adding product");
    });
};
```

## Displaying Product Images

### On the Home Page (Product Listing)

After fetching all products from `GET /api/products` (via Context), the `Home` component fetches each product's image individually:

```jsx
const response = await axios.get(
  `http://localhost:8080/api/product/${product.id}/image`,
  { responseType: "blob" }
);
const imageUrl = URL.createObjectURL(response.data);
```

- Each product card displays its image as a `<img src={imageUrl} />`
- If an image fetch fails, a placeholder URL is used as fallback
- `URL.createObjectURL()` creates a temporary browser URL from the binary blob

### On the Product Detail Page

The `Product` component fetches the product data from `GET /api/product/{id}`, then conditionally fetches the image if `response.data.imageName` exists:

```jsx
const fetchImage = async () => {
  const response = await axios.get(
    `http://localhost:8080/api/product/${id}/image`,
    { responseType: "blob" }
  );
  setImageUrl(URL.createObjectURL(response.data));
};
```

### In the Shopping Cart

The `Cart` component follows the same pattern — iterating over cart items and fetching each product's image blob to display thumbnails.

## Global State Management

The `AppContext` (React Context) provides:

| Value                | Description                                     |
| -------------------- | ----------------------------------------------- |
| `data`               | Product list from `GET /api/products`           |
| `isError`            | Fetch error flag                                |
| `cart`               | Shopping cart items (persisted in localStorage) |
| `addToCart(product)` | Add item to cart                                |
| `removeFromCart(id)` | Remove item from cart                           |
| `clearCart()`        | Empty the cart                                  |
| `refreshData()`      | Re-fetch products from the API                  |

## Key Dependencies

| Dependency       | Purpose                              |
| ---------------- | ------------------------------------ |
| **Axios**        | HTTP client for backend API requests |
| **React Router** | Client-side routing (SPA navigation) |
| **Bootstrap**    | UI component library and styling     |
| **Sass**         | CSS pre-processor                    |

## Backend API

This frontend communicates with the backend API at `http://localhost:8080/api`. See the [`ecom-proj` README](../ecom-proj/README.md) for full API documentation.

| Method | Endpoint                  | Usage                                  |
| ------ | ------------------------- | -------------------------------------- |
| GET    | `/api/products`           | Fetch all products                     |
| GET    | `/api/product/{id}`       | Fetch single product                   |
| GET    | `/api/product/{id}/image` | Fetch product image blob               |
| POST   | `/api/product`            | Add new product with image (multipart) |

## Key Takeaways

- **Multipart Image Upload** — Using `FormData` with `Blob` allows sending both JSON and binary data in a single POST request.
- **Blob URLs for Image Display** — Fetching images as blobs and creating object URLs with `URL.createObjectURL()` enables displaying database-stored images without base64 encoding.
- **Per-Product Image Fetching** — Each product's image is fetched individually after the product list is loaded, keeping the initial product fetch lightweight.
- **Fallback Handling** — When an image fetch fails, a placeholder URL ensures the UI remains functional.
- **Frontend-Backend Collaboration** — The frontend sends `"imageFile"` and `"product"` parts in the multipart request, which the backend consumes via `@RequestPart`, requiring alignment on part names and data formats.
- **Iterative Feature Development** — Building the image feature incrementally (form UI, then API integration, then image display, then cart/checkout) helped identify and resolve issues early.

## Lessons Learned

- **Image Handling Complexity** — Managing image uploads involves additional complexity, such as ensuring correct format and size, and handling potential security risks.
- **Importance of Images in E-Commerce** — Including images significantly enhances the user experience on e-commerce platforms, making products more tangible and appealing.
- **Debugging Multipart Requests** — Issues with data not being sent correctly from the client require checking both the `FormData` structure on the frontend and the `@RequestPart` parameters on the backend.
- **Naming Consistency** — Variable names and part keys must match between frontend and backend to ensure proper data binding (e.g., part name `"imageFile"` must match `@RequestPart MultipartFile imageFile`).
- **Continuous Improvement** — Future enhancements include handling duplicate products, update and delete functionalities, and optimizing image loading performance.
