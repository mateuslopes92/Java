# Spring OAuth2 Study Project

OAuth2 is an **authorization framework** that allows applications to obtain limited access to user accounts on an HTTP service (Google, GitHub, Facebook, etc.) without exposing the user's password.

This project demonstrates **OAuth2 login** with Spring Security, allowing users to authenticate via Google or GitHub instead of a traditional username/password flow.

- Java 25
- Maven
- Spring Boot 4.1.0
- Spring Security OAuth2 Client
- Spring Web MVC

---

## What is OAuth2?

OAuth2 is a protocol that allows third-party applications to access user resources without sharing credentials. Instead of entering a password, the user authorizes the application through an **authorization server** (Google, GitHub, etc.).

### Key Roles

| Role | Description |
|------|-------------|
| **Resource Owner** | The user who owns the account |
| **Client** | The application requesting access (this project) |
| **Authorization Server** | The service that authenticates the user and issues tokens (Google, GitHub) |
| **Resource Server** | The service that hosts the user's data (often the same as the authorization server) |

### OAuth2 Flow (Authorization Code Grant)

```
User → Client App → Google/GitHub Login → User Authenticates
    → Authorization Code → Client App → Token Request
    → Access Token → Client App → Access Protected Resources
```

Spring Security handles most of this flow automatically. The developer just configures the client registration details.

---

## What's in the code

### SpringOauth2Application.java

```java
@SpringBootApplication
public class SpringOauth2Application {
    public static void main(String[] args) {
        SpringApplication.run(SpringOauth2Application.class, args);
    }
}
```

Standard Spring Boot entry point. The `@SpringBootApplication` annotation enables auto-configuration, which includes OAuth2 auto-configuration when the OAuth2 client dependency is on the classpath.

### HelloController.java

```java
@RestController
public class HelloController {
    @GetMapping("/")
    public String greet() {
        return "Hello oauth spring";
    }
}
```

A single endpoint at `GET /` that returns a greeting. This endpoint requires authentication. Unauthenticated users are redirected to the OAuth2 provider's login page.

### SpringConfig.java

```java
@Configuration
@EnableWebSecurity
public class SpringConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        http
            .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
            .oauth2Login(Customizer.withDefaults());
        return http.build();
    }
}
```

| Configuration | Meaning |
|--------------|---------|
| `anyRequest().authenticated()` | Every request requires authentication |
| `oauth2Login(Customizer.withDefaults())` | Enables OAuth2 login with default settings |

Spring Security automatically:
1. Detects unauthenticated requests
2. Redirects the user to the OAuth2 provider's login page
3. Handles the callback (redirect URI)
4. Creates a session (JSESSIONID cookie) upon successful authentication

---

## How OAuth2 Login Works

The OAuth2 login flow with Spring Security works as follows:

```
1. User visits http://localhost:8080/
2. Spring Security detects no authentication
3. Redirects to Google/GitHub login page
4. User logs in on the provider's site
5. Provider redirects back to http://localhost:8080/login/oauth2/code/google
6. Spring Security exchanges the authorization code for an access token
7. User is authenticated → redirects back to /
8. Browser now has a JSESSIONID cookie → subsequent requests are authenticated
```

---

## Configuration

### application.properties

```properties
spring.application.name=SpringOauth2

spring.security.oauth2.client.registration.google.client-id=
spring.security.oauth2.client.registration.google.client-secret=

spring.security.oauth2.client.registration.github.client-id=
spring.security.oauth2.client.registration.github.client-secret=
```

The client registrations for **Google** and **GitHub** are configured but require real credentials to function.

### Obtaining Credentials

#### Google
1. Go to [Google Cloud Console](https://console.cloud.google.com/)
2. Create a new project or select an existing one
3. Navigate to **APIs & Services** → **Credentials**
4. Click **Create Credentials** → **OAuth 2.0 Client IDs**
5. Set **Application type** to **Web application**
6. Add `http://localhost:8080/login/oauth2/code/google` as an Authorized redirect URI
7. Copy the **Client ID** and **Client Secret** into `application.properties`

#### GitHub
1. Go to [GitHub Settings](https://github.com/settings/developers) → **Developer settings** → **OAuth Apps**
2. Click **New OAuth App**
3. Set **Authorization callback URL** to `http://localhost:8080/login/oauth2/code/github`
4. Copy the **Client ID** and **Client Secret** into `application.properties`

---

## Dependencies

```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>4.1.0</version>
</parent>
```

| Dependency | Purpose |
|---|---|
| `spring-boot-starter-security-oauth2-client` | OAuth2 client support — login via Google, GitHub, etc. |
| `spring-boot-starter-webmvc` | Core Spring MVC for web endpoints |
| `spring-boot-starter-security-oauth2-client-test` | Test utilities for OAuth2 client (test scope) |
| `spring-boot-starter-webmvc-test` | Test utilities for Web MVC (test scope) |

---

## Running the Application

```bash
# Fill in your OAuth2 credentials first
# Then run:
./mvnw spring-boot:run
```

The application will be available at `http://localhost:8080/`.

Open it in a browser — you'll be redirected to the OAuth2 provider's login page. After authenticating, you'll see the greeting message.

---

## Key Takeaways

- **OAuth2 Login** — Spring Security makes OAuth2 login straightforward. With just a few lines of configuration, users can authenticate via Google, GitHub, or any OAuth2 provider.
- **Spring Security Auto-Configuration** — When `spring-boot-starter-security-oauth2-client` is on the classpath, Spring Boot automatically configures the OAuth2 login flow.
- **Session-Based Authentication** — After OAuth2 login, Spring Security creates a traditional session (JSESSIONID), so subsequent requests are authenticated via the session cookie — not the OAuth2 token.
- **Authorization Code Grant** — Spring Security uses the Authorization Code grant type, the most secure OAuth2 flow, where the client never sees the user's password.
- **No Password Management** — The application never handles user passwords. Authentication is delegated entirely to the OAuth2 provider.
- **Configuration Required** — OAuth2 credentials (client-id and client-secret) must be obtained from each provider and configured in `application.properties`. These are sensitive values and should not be committed to version control.
- **Redirect URIs** — The redirect URI (`/login/oauth2/code/{provider}`) must be registered with the OAuth2 provider exactly as Spring Security expects it.
