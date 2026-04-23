# Java 25 New features
This project is to study and test new features of new JDK.


This project uses **Java 25** and includes features that are still in **preview**.  
To compile and run the project correctly, you must enable preview features.

---

### 1. Install and Configure JDK 25

In IntelliJ:

- Go to: `File → Project Structure → Project`
- Set:
    - **Project SDK** → `Java 25`
    - **Language Level** → `25 (Preview)` (if available)

Recommended JDK distributions:
- Microsoft OpenJDK
- JetBrains Runtime
- IBM Semeru

---

### 2. Enable Preview Features (IntelliJ)

- Open **Run Configuration**
- Add to **VM Options**:

```bash
--enable-preview
```

---

### 3. Maven Configuration 

The pom.xml is configured to enable preview features:
```bash
<properties>
    <maven.compiler.release>25</maven.compiler.release>
</properties>

<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-compiler-plugin</artifactId>
    <version>3.11.0</version>
    <configuration>
        <compilerArgs>
            <arg>--enable-preview</arg>
        </compilerArgs>
    </configuration>
</plugin>
```
Important Notes
- Preview features are not enabled by default, even in Java 25
- You must enable them both at:
  - compile time (Maven)
  - runtime (VM options)
  - Some features may change in future Java versions

### 4. Run via CLI (optional)
If running manually:

mvn clean compile
```bash
java --enable-preview -cp target/classes org.example.Main
```

--- 

## Virtual Threads (VirtualThreadExample)

This example shows how to use Virtual Threads in Java.

Virtual Threads are a new feature that allows creating lightweight threads.  
They are much cheaper than traditional threads and are managed by the JVM.

---

### What this example does

- Creates multiple virtual threads
- Each thread runs a small task
- Simulates work using `Thread.sleep`
- Waits for all threads to finish using `join()`

---

### Example

```java
Thread thread = Thread.startVirtualThread(() -> {
    System.out.println("Running in virtual thread");
});
```

### Important points

Virtual threads are created using:
```java
Thread.startVirtualThread(...)
```
- They are lightweight compared to normal threads
- You can create many of them (even thousands)
- They are good for tasks that wait (like API calls or database calls)

### Difference from normal threads

- Normal threads:
  - Heavy
  - Limited number

- Virtual threads:
  - Lightweight
  - Can scale to many threads
  
### Conclusion
Virtual Threads make it easier to write concurrent code without worrying too much about performance and thread limits.

--- 

## Pattern Matching

This example shows how Pattern Matching works in Java.

Pattern Matching makes code simpler when checking types and casting objects.

---

### What this example does

- Checks the type of an object using `instanceof`
- Uses `switch` to handle different types
- Avoids manual casting

---

### Example

```java
if (obj instanceof String s) {
    System.out.println(s.length());
}
```

### Pattern Matching with switch
```java
return switch (obj) {
    case String s -> "This is a String: " + s;
    case Integer i -> "This is an Integer: " + i;
    case null -> "Value is null";
    default -> "Unknown type";
};
```

### Important points
- You can declare a variable inside `instanceof`
- No need to cast manually
- `switch` can now work with object types
- Code becomes cleaner and easier to read

### Before vs After

Old way:
```java
if (obj instanceof String) {
    String s = (String) obj;
}
```

New way:
```java
if (obj instanceof String s) {
}
```

### Conclusion
Pattern Matching reduces boilerplate code and makes type checking safer and more readable.

---

## Record Patterns

This example shows how to use Record Patterns in Java.

Record Patterns allow you to extract values from objects (records) directly when checking their type.

---

### What this example does

- Creates a `record` (User)
- Uses pattern matching to check the type
- Extracts fields (name and age) directly
- Uses record patterns with `instanceof` and `switch`

---

### Example

```java
if (obj instanceof User(String name, int age)) {
    System.out.println(name);
}
Record example
record User(String name, int age) {}
Using with switch
return switch (obj) {
    case User(String name, int age) ->
        "User -> name: " + name + ", age: " + age;
    default ->
        "Unknown type";
};
```

Important points
- Records are simple classes used to store data
- Record patterns let you extract values directly
- No need to call getters like user.name()
- Works with instanceof and switch

### Before vs After

#### Old way:
```java 
if (obj instanceof User) {
    User u = (User) obj;
    System.out.println(u.name());
}
```

#### New way:
```java 
if (obj instanceof User(String name, int age)) {
    System.out.println(name);
}
```

#### Conclusion
Record Patterns make it easier to work with objects by combining type checking and data extraction in a single step.

--- 

## Sealed Classes

This example shows how Sealed Classes work in Java.
Sealed Classes allow you to control which classes can extend or implement another class or interface.

---

### What this example does

- Creates a sealed interface (`Payment`)
- Defines which classes are allowed to implement it
- Uses `switch` to handle all possible types

---

### Example

```java
sealed interface Payment permits CreditCard, Pix, Cash {}
```

### Permitted classes
```java
final class CreditCard implements Payment {}
final class Pix implements Payment {}
non-sealed class Cash implements Payment {}
```

### Important keywords
- `sealed` → restricts who can extend or implement
- `permits` → defines allowed classes
- `final` → cannot be extended
- `non-sealed` → can be extended again

### Using with switch
```java
return switch (payment) {
    case CreditCard c -> "Credit Card";
    case Pix p -> "Pix";
    case Cash c -> "Cash";
};
```

### Important points
- Only permitted classes can implement the sealed type
- The compiler knows all possible types
- No need for default in switch (if all cases are covered)
- Helps make code safer and more predictable

### Conclusion
Sealed Classes help control inheritance and make code easier to understand and maintain.

---

