# GOF Design Patterns
GoF stands for Gang of Four.

It refers to the four authors of the classic design patterns book:

- Erich Gamma
- Richard Helm
- Ralph Johnson
- John Vlissides

GoF = the 4 guys who defined the design patterns

They wrote the famous book:
- Design Patterns: Elements of Reusable Object-Oriented Software

### Why it matters
- This book introduced the 23 classic design patterns
- It became the foundation of modern software design
- When people say “GoF patterns”, they mean those 23 patterns

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

## Abstract Factory
This pattern is a creational pattern.
- Provides an interface for creating families of related objects.
- Ensures that related objects are used together.
- Encapsulates a group of individual factories.
- Promotes consistency between products.

Eg: Creating UI components based on a theme (light or dark), where all components follow the same style.

```java
// Abstract Products
interface Button {
    void render();
}

interface Checkbox {
    void render();
}

// Concrete Products (Light Theme)
class LightButton implements Button {
    public void render() {
        System.out.println("Rendering Light Button");
    }
}

class LightCheckbox implements Checkbox {
    public void render() {
        System.out.println("Rendering Light Checkbox");
    }
}

// Concrete Products (Dark Theme)
class DarkButton implements Button {
    public void render() {
        System.out.println("Rendering Dark Button");
    }
}

class DarkCheckbox implements Checkbox {
    public void render() {
        System.out.println("Rendering Dark Checkbox");
    }
}

// Abstract Factory
interface UIFactory {
    Button createButton();
    Checkbox createCheckbox();
}

// Concrete Factories
class LightThemeFactory implements UIFactory {
    public Button createButton() {
        return new LightButton();
    }

    public Checkbox createCheckbox() {
        return new LightCheckbox();
    }
}

class DarkThemeFactory implements UIFactory {
    public Button createButton() {
        return new DarkButton();
    }

    public Checkbox createCheckbox() {
        return new DarkCheckbox();
    }
}

// Factory Producer
class FactoryProducer {
    public static UIFactory getFactory(String theme) {

        if (theme.equalsIgnoreCase("light")) {
            return new LightThemeFactory();
        } else if (theme.equalsIgnoreCase("dark")) {
            return new DarkThemeFactory();
        }

        throw new IllegalArgumentException("Unknown theme");
    }
}
```

- The abstract factory groups multiple factories together.
- Clients use a factory without knowing the concrete implementations.
- Ensures that objects created belong to the same family.

#### Conclusion
- Should be used when your system needs to work with multiple families of related objects
- Helps enforce consistency between related objects
- Makes switching between product families easy (e.g., light → dark theme)
- Adds an extra abstraction layer compared to Factory Method