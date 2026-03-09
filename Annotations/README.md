# Annotations in Java

- All the annotations starts with '@' and then the name of the annotation (eg. @SuppressWarnings).
- Annotations can receive parameters
- Annotations can be added to anything in java: Classes, Variables, Methods, Methods Parameters and even other annotations.

## Creating your own annotation
Im creating an annotation called `@VeryImportant` to show how to create an custom annotation in java

- use @interface on declaration to create a custom annotation
- you can use other annotations to customize your annotation
- use `@Target()` to specify exactly the kind of Java element to use it
- use `@Retention` to specify when the annotation will be ran

### @interface
Defines that will be an annotation:
```java
// To create an annotation is just use @interface instead of class
public @interface VeryImportant {
}

```

### @Target
Using @Target to specify the kind of java element that can use the annotation
```java
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

// To create an annotation is just use @interface instead of class
@Target({ElementType.TYPE, ElementType.METHOD}) // Target specify which kind of java element can use the annotation
public @interface VeryImportant {
}
```

### Retention
