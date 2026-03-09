import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

// To create an annotation is just use @interface instead of class
@Target({ElementType.TYPE, ElementType.METHOD}) // Target specify which kind of java element can use the annotation
public @interface VeryImportant {
}
