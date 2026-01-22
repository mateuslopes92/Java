import java.lang.reflect.Field;

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
    }
}