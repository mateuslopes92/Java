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
        <artifactId>junit-jupiter</artifactId>
        <version>5.12.2</version>
        <scope>test</scope>
    </dependency>
</dependencies>
```

- `junit-jupiter` is the JUnit 5 implementation.
- The `test` scope means the dependency is only used when running tests.

---

# Basic Structure of a Test

A test normally follows the **Arrange → Act → Assert** pattern.

1. **Arrange** – create objects and prepare data
2. **Act** – execute the method being tested
3. **Assert** – verify the expected result

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

# Test Lifecycle Annotations

JUnit provides annotations to run setup and cleanup logic.

## `@BeforeEach`

Runs **before every test method**.

```java
@BeforeEach
void setUp() {
    calculator = new SimpleCalculator();
}
```

This helps avoid repeating object creation in every test.

---

## `@AfterEach`

Runs **after each test**.

```java
@AfterEach
void tearDown() {
    System.out.println("Test finished");
}
```

---

## `@BeforeAll`

Runs **once before all tests**.

```java
@BeforeAll
static void init(){
    System.out.println("Starting tests");
}
```

---

## `@AfterAll`

Runs **once after all tests**.

```java
@AfterAll
static void cleanup(){
    System.out.println("All tests finished");
}
```

---

# Display Names

`@DisplayName` allows tests to have more readable descriptions.

```java
@DisplayName("2 + 2 should equal 4")
@Test
void addTest(){
    assertEquals(4, calculator.add(2,2));
}
```

---

# Assertions

Assertions verify that the **expected result matches the actual result**.

JUnit provides many assertions inside:

```java
org.junit.jupiter.api.Assertions
```

Common assertions include:

- `assertEquals(expected, actual)`
- `assertTrue(condition)`
- `assertFalse(condition)`
- `assertNull(object)`
- `assertNotNull(object)`
- `assertThrows(exception, executable)`
- `assertAll(...)`

Example:

```java
assertEquals(4, result);
```

---

# Parameterized Tests

Parameterized tests allow running the **same test with multiple inputs**.

## `@ValueSource`

```java
@ParameterizedTest
@ValueSource(ints = {0, 10, 30, 59})
void shouldReturnF(int score){
    assertEquals('F', grader.determineLetterGrade(score));
}
```

---

## `@CsvSource`

Allows passing **multiple parameters**.

```java
@ParameterizedTest
@CsvSource({
    "69, D",
    "79, C",
    "89, B",
    "99, A"
})
void shouldReturnCorrectGrade(int score, char expected){
    assertEquals(expected, grader.determineLetterGrade(score));
}
```

This is very useful when testing **multiple combinations of input/output**.

---

# Testing a Simple Method

## Class being tested

```java
package org.example;

public class SimpleCalculator {

    public int add(int numberA, int numberB){
        return numberA + numberB;
    }

}
```

---

# SimpleCalculator Tests

```java
package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimpleCalculatorTest {

    private SimpleCalculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new SimpleCalculator();
    }

    @Test
    @DisplayName("2 + 2 should equal 4")
    void twoPlusTwoShouldEqualFour(){
        int result = calculator.add(2,2);
        assertEquals(4, result);
    }

    @Test
    @DisplayName("3 + 7 should equal 10")
    void threePlusSevenShouldEqualTen(){
        int result = calculator.add(3,7);
        assertEquals(10, result);
    }

    @Test
    @DisplayName("Addition results should always be positive")
    void resultShouldBePositive(){
        int result = calculator.add(5,5);
        assertTrue(result > 0);
    }

    @Test
    @DisplayName("Multiple assertions example")
    void multipleAssertions(){
        int result = calculator.add(4,6);

        assertAll(
                () -> assertEquals(10, result),
                () -> assertTrue(result > 0)
        );
    }
}
```

---

# Testing Conditional Logic

## Class being tested

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
|------|------|
| < 60 | F |
| 60–69 | D |
| 70–79 | C |
| 80–89 | B |
| ≥ 90 | A |

---

# Grader Tests

```java
package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class GraderTest {

    private Grader grader;

    @BeforeEach
    void setUp() {
        grader = new Grader();
    }

    @ParameterizedTest
    @DisplayName("Grades below 60 should return F")
    @ValueSource(ints = {0, 10, 30, 59})
    void shouldReturnF(int score){
        assertEquals('F', grader.determineLetterGrade(score));
    }

    @ParameterizedTest
    @DisplayName("Verify correct letter grades")
    @CsvSource({
            "69, D",
            "79, C",
            "89, B",
            "99, A"
    })
    void shouldReturnCorrectGrade(int score, char expectedGrade){
        assertEquals(expectedGrade, grader.determineLetterGrade(score));
    }

    @Test
    @DisplayName("Negative grades should throw exception")
    void negativeGradeShouldThrowException(){
        assertThrows(
                IllegalArgumentException.class,
                () -> grader.determineLetterGrade(-1)
        );
    }

    @Test
    @DisplayName("Grade 100 should still return A")
    void hundredShouldReturnA(){
        assertEquals('A', grader.determineLetterGrade(100));
    }
}
```

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

Key features used in this project:

- `@Test`
- `@BeforeEach`
- `@DisplayName`
- `@ParameterizedTest`
- `@ValueSource`
- `@CsvSource`
- `assertEquals`
- `assertTrue`
- `assertThrows`
- `assertAll`