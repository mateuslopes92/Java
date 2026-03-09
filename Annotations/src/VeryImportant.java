import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// To create an annotation is just use @interface instead of class
@Target({ElementType.TYPE, ElementType.METHOD}) // Target specifies which kind of Java element can use the annotation
@Retention(RetentionPolicy.RUNTIME) // Retention defines how long the annotation is kept. RUNTIME means it is available during program execution and can be accessed via reflection
public @interface VeryImportant {
}