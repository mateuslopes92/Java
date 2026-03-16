# JUnit 5 with Java

- **JUnit** is a testing framework for Java used to write and run automated tests.
- **JUnit 5** is the latest major version and introduces a modular architecture and improved annotations.
- Tests help verify that small units of code (methods or classes) behave as expected.

JUnit tests are usually placed in the **test folder** and validate the behavior of the application code.

---

# JUnit 5 Dependency (Maven)

To use JUnit 5 in a Maven project, add the dependency in the `pom.xml`.

```xml
<dependencies>
    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter-engine</artifactId>
        <version>5.12.2</version>
        <scope>test</scope>
    </dependency>
</dependencies>
```

- `junit-jupiter` is the JUnit 5 implementation.
- The `test` scope means the dependency is only used when running tests.

---

# Basic Structure of a Test

A test normally follows the **Arrange → Act → Assert** pattern:

1. **Arrange** – create objects and prepare data.
2. **Act** – execute the method being tested.
3. **Assert** – verify the expected result.

Example:

```java
@Test
void twoPlusTwoShouldEqualFour(){
    SimpleCalculator calculator = new SimpleCalculator(); // Arrange
    int result = calculator.add(2, 2); // Act

    assertEquals(4, result); // Assert
}
```

---

# The `@Test` Annotation

The `@Test` annotation tells JUnit that the method is a **test method**.

```java
import org.junit.jupiter.api.Test;

@Test
void twoPlusTwoShouldEqualFour(){
    SimpleCalculator calculator = new SimpleCalculator();
    int result = calculator.add(2, 2);

    assertEquals(4, result);
}
```

Important notes:

- Test methods usually return **void**
- They should have **descriptive names**
- Each test should verify **one behavior**

---

# Assertions

Assertions are used to verify that the **expected result matches the actual result**.

JUnit provides many assertion methods inside:

```java
org.junit.jupiter.api.Assertions
```

Common assertions include:

- `assertEquals(expected, actual)`
- `assertThrows(exception, executable)`
- `assertTrue(condition)`
- `assertFalse(condition)`

Example:

```java
assertEquals(4, result);
```

This verifies that the value returned by the method equals `4`.

---

# Testing a Simple Method

### Class being tested

```java
package org.example;

public class SimpleCalculator {

    public int add(int numberA, int numberB){
        return numberA + numberB;
    }

}
```

### Test Class

```java
package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SimpleCalculatorTest {

    @Test
    void twoPlusTwoShouldEqualFour(){
        SimpleCalculator calculator = new SimpleCalculator();
        int result = calculator.add(2, 2);

        assertEquals(4, result);
    }

    @Test
    void threePlusSevenShouldEqualTen(){
        SimpleCalculator calculator = new SimpleCalculator();
        int result = calculator.add(3, 7);

        assertEquals(10, result);
    }
}
```

What these tests verify:

- `2 + 2` returns `4`
- `3 + 7` returns `10`

---

# Testing Conditional Logic

Now testing a class with **multiple conditions**.

### Class being tested

```java
package org.example;

public class Grader {

    public char determineLetterGrade (int numberGrade){
        if(numberGrade < 0){
            throw new IllegalArgumentException("Number grade cannot be less than 0");
        }
        else if (numberGrade < 60){
            return 'F';
        } else if (numberGrade < 70){
            return 'D';
        } else if (numberGrade < 80){
            return 'C';
        } else if (numberGrade < 90){
            return 'B';
        } else {
            return 'A';
        }
    }

}
```

This method converts a **numeric grade into a letter grade**.

| Score | Grade |
| ----- | ----- |
| < 60  | F     |
| 60–69 | D     |
| 70–79 | C     |
| 80–89 | B     |
| ≥ 90  | A     |

---

# Testing Different Scenarios

To properly test the method we should cover **multiple cases**.

### Test Class

```java
package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GraderTest {

    @Test
    void fifthNineShouldReturnF() {
        var grader = new Grader();
        var result = grader.determineLetterGrade(59);

        assertEquals('F', result);
    }

    @Test
    void sixtyNineShouldReturnD() {
        var grader = new Grader();
        var result = grader.determineLetterGrade(69);

        assertEquals('D', result);
    }

    @Test
    void senventyNineShouldReturnC() {
        var grader = new Grader();
        var result = grader.determineLetterGrade(79);

        assertEquals('C', result);
    }

    @Test
    void eightyNineShouldReturnB() {
        var grader = new Grader();
        var result = grader.determineLetterGrade(89);

        assertEquals('B', result);
    }

    @Test
    void ninetyNineShouldReturnA() {
        var grader = new Grader();
        var result = grader.determineLetterGrade(99);

        assertEquals('A', result);
    }
}
```

Each test verifies a **specific boundary of the grading logic**.

---

# Testing Exceptions

JUnit also allows testing if a method throws an exception.

In this case, the method should throw an exception if the grade is **less than 0**.

### Method behavior

```java
if(numberGrade < 0){
    throw new IllegalArgumentException("Number grade cannot be less than 0");
}
```

### Test

```java
@Test
void negativeOneShouldReturnIllegalArgumentException() {
    var grader = new Grader();

    assertThrows(
        IllegalArgumentException.class,
        () -> { grader.determineLetterGrade(-1); }
    );
}
```

Explanation:

- `assertThrows` verifies that the method throws the expected exception.
- The code being tested is passed as a **lambda expression**.

---

# Project Structure Example

Typical Maven project structure:

```
src
 ├── main
 │   └── java
 │        └── org.example
 │            ├── Grader.java
 │            └── SimpleCalculator.java
 │
 └── test
     └── java
          └── org.example
              ├── GraderTest.java
              └── SimpleCalculatorTest.java
```

- `main` contains **application code**
- `test` contains **unit tests**

---

# Summary

JUnit 5 allows you to:

- Write automated tests for Java code
- Validate expected behavior
- Detect bugs early
- Improve code reliability

Key concepts used in this project:

- `@Test`
- `assertEquals`
- `assertThrows`
- Unit testing small methods
- Testing conditional logic
- Testing exceptions