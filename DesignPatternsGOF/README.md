# GOF Design Patterns

## Creational Patterns

### Singleton
This pattern is a creational pattern.
- Ensures that only one instalce of its kind exists.
- Provides a single point of access to it.
- Let you access your object from anywhere in your application
- Guarantee that only one instace of this class will be availabe at any point of time 

Eg: A database instance.

```java
class Singleton {

    // Static variable reference of single_instance
    // of type Singleton
    private static volatile Singleton single_instance = null;

    // Declaring a variable of type String
    private String s;

    // Constructor
    // Here we will be creating private constructor
    // restricted to this class itself
    private Singleton(){
        s = "String from the Singleton class";
    }

    // Static method
    // Static method to create instance of Singleton class
    // The syncrhronized ensures each thread wait for its time, it locks it
    public static Singleton getInstance() {
        if(single_instance == null){
            synchronized (Singleton.class){
                if(single_instance == null){
                    single_instance = new Singleton();
                }
            }
        }

        return single_instance;
    }
}
```
- All inside the Singleton are private and has a static method getInstance to return the single instance.
- The volatile in the instance variable with the syncronized block ensures that is thread safe.

#### Conclusion
- Should be used when a class must have a single instance available
- Returns the existing instance if it has already been created
- Needs to handle multiple threads to be more robust


## Factory Method
This pattern is a creational pattern.
- Provides an interface for creating objects.
- Lets a class decide which object to create.
- Encapsulates object creation logic.
- Promotes loose coupling by avoiding direct instantiation (`new`).

Eg: Creating different types of objects like shapes, payments, notifications.

```java
interface Shape {
    void draw();
}

class Circle implements Shape {
    public void draw() {
        System.out.println("Drawing a Circle");
    }
}

class Square implements Shape {
    public void draw() {
        System.out.println("Drawing a Square");
    }
}

class Triangle implements Shape {
    public void draw() {
        System.out.println("Drawing a Triangle");
    }
}

class ShapeFactory {

    public static Shape getShape(String shapeType) {

        if (shapeType == null) {
            return null;
        }

        switch (shapeType.toLowerCase()) {
            case "circle":
                return new Circle();
            case "square":
                return new Square();
            case "triangle":
                return new Triangle();
            default:
                throw new IllegalArgumentException("Unknown shape type: " + shapeType);
        }
    }
}
```

- The factory centralizes object creation in one place.
- Clients don’t need to know the concrete classes being instantiated.
- The logic for choosing which object to create is encapsulated.

#### Conclusion
- Should be used when the creation logic is complex or may change
- Helps reduce tight coupling between classes
- Makes code easier to extend (add new types without changing client code)
- Returns objects based on input instead of direct instantiation