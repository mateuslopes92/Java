import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
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
    }
}