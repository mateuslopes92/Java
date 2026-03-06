# Annotations in Java

- All the annotations starts with '@' and then the name of the annotation (eg. @SuppressWarnings).
- Annotations can receive parameters
- Annotations can be added to anything in java: Classes, Variables, Methods, Methods Parameters and even other annotations.

## Creating your own annotation
Im creating an annotation called `@VeryImportant` to show how to create an custom annotation in java

- use @interface on declaration to create a custom annotation
- you can use other annotations to customize your annotation

```java
// To create an annotation is just use @interface instead of class
public @interface VeryImportant {
}

```

