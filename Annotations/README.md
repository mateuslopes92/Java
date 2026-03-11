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
It tells the compiler and JVM at what stage the annotation should be retained.

```java
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// To create an annotation is just use @interface instead of class
@Target({ElementType.TYPE, ElementType.METHOD}) // Target specifies which kind of Java element can use the annotation
@Retention(RetentionPolicy.RUNTIME) // Retention defines how long the annotation is kept. RUNTIME means it is available during program execution and can be accessed via reflection
public @interface VeryImportant {
}
```

### Processing the Annotation
```java
public class Main {
    public static void main(String[] args) {

        Dog myDog = new Dog("Jayne");
        Cat myCat = new Cat("Mary");

        // Processing Annotation
        // Look at the class and check weather the class was marked with VeryImportant annotation
        // We can do whatever we want if sees the annotation on that class
        // The class has the annotation not the object
//       if (myDog.getClass().isAnnotationPresent(VeryImportant.class)){
        if (myCat.getClass().isAnnotationPresent(VeryImportant.class)){
           System.out.println("This thing is very important!");
       } else {
           System.out.println("This thing is not very important!");
       }
    }
}
```

## Method only Annotation
The annotation @RunImmediately is to run only on methods example 

```java
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RunImmediately {

}
```

In our main class we verify if the method is annotated with that annotation and run the method if is

```java
// Running method only annotation
for (Method method : myDog.getClass().getDeclaredMethods()){
    if(method.isAnnotationPresent(RunImmediately.class)){
        try {
            method.invoke(myDog);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
```

## Annotation Parameters
We can add parameters to our annotation by adding a field to it
NOTE: The fields in a annotation should be a method, but it acts like a field

```java
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RunImmediately {
    int times(); // we need to make a method but it acts like a normal field
}

```

Then we can add the parameter where the annotation is being called
Is just pass as parameter to our annotation `@RunImmediately(times = 3)`
```java
@VeryImportant
public class Dog {
    String name;
    int age;

    public Dog(String name){
        this.name = name;
    }

//    @VeryImportant
    @RunImmediately(times = 3)
    public void bark(){
        System.out.println("Ruf!");
    }

    public void eat(){
        System.out.println("Munch");
    }
}
```

Then on our main class we can check the annotation parameter and do whatever we want

```java
// Running method only annotation
// checking annotation parameters
for (Method method : myDog.getClass().getDeclaredMethods()){
    if(method.isAnnotationPresent(RunImmediately.class)){
        try {
            RunImmediately annotation = method.getAnnotation(RunImmediately.class);

            for(int i = 0; i < annotation.times(); i++){
                method.invoke(myDog);
            }
            
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
```

### Important notes about annotation parameters
- Should be a primitive type 
- Class type
- String
- or an array of the above eg.(`int[] times()`)
- You can specify a default value like: `int times() default 1`
- If you dont have a default you must pass the parameter when using the annotation