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