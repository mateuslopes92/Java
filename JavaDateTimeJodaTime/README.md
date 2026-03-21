# Java Date Time API + Joda-Time

* Handling dates and times in Java has always been complex due to **timezones, formatting, and mutability issues**.
* Before Java 8, developers relied on older APIs like `Date` and `Calendar`, which were difficult to use.
* **Joda-Time** was created to solve these problems and became the de facto standard.
* Java 8 introduced the **`java.time` API**, inspired by Joda-Time, as the modern solution.

This project demonstrates both approaches and compares them.

---

# Joda-Time Dependency (Maven)

To use Joda-Time in a Maven project, add the dependency in the `pom.xml`.

```xml
<dependencies>
    <dependency>
        <groupId>joda-time</groupId>
        <artifactId>joda-time</artifactId>
        <version>2.12.7</version>
    </dependency>
</dependencies>
```

* `joda-time` is an external library for handling dates and times.
* It is now in **maintenance mode** and not recommended for new projects.

---

# Java Time API (java.time)

The `java.time` package is the **modern date and time API** introduced in Java 8.

## Example

```java
import java.time.*;
import java.time.format.DateTimeFormatter;

public class JavaTimeExample {

    public static void main(String[] args) {

        LocalDateTime now = LocalDateTime.now();
        System.out.println("Now: " + now);

        LocalDate date = LocalDate.of(2026, 3, 21);
        System.out.println("Date: " + date);

        ZonedDateTime zoned = ZonedDateTime.now(ZoneId.of("America/Sao_Paulo"));
        System.out.println("Brazil Time: " + zoned);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        System.out.println("Formatted: " + now.format(formatter));

        LocalDateTime future = now.plusDays(5).plusHours(3);
        System.out.println("Future: " + future);

        Duration duration = Duration.between(now, future);
        System.out.println("Hours difference: " + duration.toHours());
    }
}
```

---

# Joda-Time

Joda-Time provides a more robust API compared to the old Java `Date` and `Calendar`.

## Example

```java
import org.joda.time.*;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class JodaTimeExample {

    public static void main(String[] args) {

        DateTime now = DateTime.now();
        System.out.println("Now: " + now);

        LocalDate date = new LocalDate(2026, 3, 21);
        System.out.println("Date: " + date);

        DateTime zoned = DateTime.now(DateTimeZone.forID("America/Sao_Paulo"));
        System.out.println("Brazil Time: " + zoned);

        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm");
        System.out.println("Formatted: " + now.toString(formatter));

        DateTime future = now.plusDays(5).plusHours(3);
        System.out.println("Future: " + future);

        Duration duration = new Duration(now, future);
        System.out.println("Hours difference: " + duration.getStandardHours());
    }
}
```

---

# Key Concepts

## Immutability

Both APIs are **immutable**, meaning:

* Objects cannot be changed after creation
* Safer for multi-threaded applications

Example:

```java
LocalDateTime now = LocalDateTime.now();
LocalDateTime future = now.plusDays(1);

// now is unchanged
```

---

## Timezones

Handling timezones correctly is one of the hardest problems in date/time systems.

```java
ZonedDateTime brazil = ZonedDateTime.now(ZoneId.of("America/Sao_Paulo"));
ZonedDateTime tokyo = ZonedDateTime.now(ZoneId.of("Asia/Tokyo"));
```

---

## Formatting Dates

Formatting allows converting date/time objects into readable strings.

```java
DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
String formatted = LocalDateTime.now().format(formatter);
```

---

# Comparison: java.time vs Joda-Time

| Feature     | java.time (Java 8+) | Joda-Time        |
| ----------- | ------------------- | ---------------- |
| Built-in    | Yes                 | No               |
| Immutable   | Yes                 | Yes              |
| Thread-safe | Yes                 | Yes              |
| Recommended | Yes                 | No               |
| Maintenance | Active              | Maintenance mode |

---

# Converting Between APIs

Sometimes you may need to convert between Joda-Time and Java Time.

```java
import java.time.Instant;
import java.util.Date;

// Java -> Joda
Instant instant = Instant.now();
Date date = Date.from(instant);
org.joda.time.DateTime joda = new org.joda.time.DateTime(date);

// Joda -> Java
Instant backToJava = Instant.ofEpochMilli(joda.getMillis());
```

---

# Project Structure

```
src
 └── main
      └── java
           └── org.example
               ├── Main.java
               ├── JavaTimeExample.java
               └── JodaTimeExample.java
```

---

# How to Run

1. Run `JavaTimeExample` to see the modern API in action
2. Run `JodaTimeExample` to compare behavior
3. Optionally use `Main.java` to call both

Example:

```java
public class Main {
    public static void main(String[] args) {
        JavaTimeExample.main(args);
        JodaTimeExample.main(args);
    }
}
```

---

# Summary

This project demonstrates:

* The evolution of date/time handling in Java
* How **Joda-Time improved the old APIs**
* How **java.time became the modern standard**

Key takeaway:

* Use **`java.time`** for all new projects
* Understand **Joda-Time** for legacy systems
