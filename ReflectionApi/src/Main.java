import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Main {
    public static void main(String[] args) throws Exception {
        Cat myCat = new Cat("Mary", 6);

        // Getting all the fields declared in the Cat class
        Field[] catFields = myCat.getClass().getDeclaredFields();
        System.out.println("-----Cat Get Fields-----");
        for(Field field: catFields){
            System.out.println(field.getName());
        }
        System.out.println("--------------------");

        System.out.println();

        // Setting objects (using reflection to force the change even private/final prop)
        System.out.println("-----Cat Set Fields-----");
        for(Field field: catFields){
            if(field.getName().equals("name")){
                // making field accessible
                field.setAccessible(true);

                // pass the object and the value
                // java make handle the exception so is need to add throws in the main method
                field.set(myCat, "Jayne");
            }
        }
        System.out.println("Name now is = " + myCat.getName());
        System.out.println("--------------------");

        System.out.println();


        // Getting all declared methods from a class
        Method[] catMethods = myCat.getClass().getDeclaredMethods();

        System.out.println("-----Cat Method Fields-----");
        for(Method method: catMethods){
            System.out.println(method.getName());
        }
        System.out.println("--------------------");

        System.out.println();

        System.out.println("-----Calling Cat Methods-----");
        for(Method method: catMethods){
            if(method.getName().equals("meow")){
                // same as set receives the object as first parameter
                // as the method dont receive parameters we dont need to pass additional parameters
                // if has parameters is just add as following parameters
                method.invoke(myCat);
            }
        }
        System.out.println("--------------------");

        System.out.println();

        System.out.println("-----Calling Private Cat Methods-----");
        for(Method method: catMethods){
            if(method.getName().equals("heyThisIsPrivate")){
                // to make a private method accessible need to set the accessibility of the method
                method.setAccessible(true);
                // same as set receives the object as first parameter
                // as the method dont receive parameters we dont need to pass additional parameters
                // if has parameters is just add as following parameters
                method.invoke(myCat);
            }
        }
        System.out.println("--------------------");

        System.out.println();

        System.out.println("-----Calling Public Static Cat Methods-----");
        for(Method method: catMethods){
            if(method.getName().equals("thisIsAPublicStaticMethod")){
                // to make a public static method accessible need to set the accessibility of the method
                method.setAccessible(true);
                // to invoke public static methods we can pass null as the object to invoke method
                method.invoke(null);
            }
        }
        System.out.println("--------------------");

        System.out.println();

        System.out.println("-----Calling Private Static Cat Methods-----");
        for(Method method: catMethods){
            if(method.getName().equals("thisIsAPrivateStaticMethod")){
                // to make a private static method accessible need to set the accessibility of the method
                method.setAccessible(true);
                // to invoke private static methods we can pass null as the object to invoke method
                method.invoke(null);
            }
        }
        System.out.println("--------------------");
    }
}