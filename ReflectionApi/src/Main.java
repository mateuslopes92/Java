import java.lang.reflect.Field;

public class Main {
    public static void main(String[] args) {
        Cat myCat = new Cat("Mary", 6);

        // Getting all the fields declared in the Cat class
        Field[] catFields = myCat.getClass().getDeclaredFields();
        System.out.println("-----Cat Get Fields-----");
        for(Field field: catFields){
            System.out.println(field.getName());
        }
        System.out.println("--------------------");

    
    }
}