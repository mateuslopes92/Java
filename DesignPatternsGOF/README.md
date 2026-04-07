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

## Builder
This pattern is a creational pattern.
- Builds complex objects step by step.
- Separates object construction from its representation.
- Allows creation of different representations of the same object.
- Improves readability using method chaining (fluent API).

Eg: Creating objects with many optional parameters like users, requests, or configurations.

```java
class User {

    // Required fields
    private final String name;

    // Optional fields
    private final int age;
    private final String email;
    private final String phone;

    // Private constructor (only Builder can create)
    private User(Builder builder) {
        this.name = builder.name;
        this.age = builder.age;
        this.email = builder.email;
        this.phone = builder.phone;
    }

    // Static Builder class
    public static class Builder {

        // Required
        private final String name;

        // Optional (default values)
        private int age = 0;
        private String email = "";
        private String phone = "";

        // Constructor with required fields
        public Builder(String name) {
            this.name = name;
        }

        // Setters for optional fields (method chaining)
        public Builder age(int age) {
            this.age = age;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        // Build method
        public User build() {
            return new User(this);
        }
    }
}
```
- The object is created step by step using the Builder.
- The constructor is private to enforce usage of the Builder.
- Method chaining makes the code more readable and flexible.

#### Conclusion
- Should be used when an object has many optional parameters
- Avoids large constructors with many arguments
- Improves code readability and maintainability
- Allows creation of immutable objects

## Prototype
This pattern is a creational pattern.
- Creates new objects by cloning existing ones.
- Avoids the cost of creating objects from scratch.
- Allows copying objects without depending on their concrete classes.
- Uses a prototype instance to generate new objects.

Eg: Cloning document templates like reports or invoices instead of creating them every time.

```java
abstract class Document implements Cloneable {

    protected String title;
    protected String content;

    abstract void show();

    public Document clone() {
        try {
            return (Document) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Cloning not supported");
        }
    }
}

class Report extends Document {

    public Report() {
        this.title = "Report Template";
        this.content = "Report Content...";
    }

    void show() {
        System.out.println("Report Title: " + title);
        System.out.println("Report Content: " + content);
    }
}

class Invoice extends Document {

    public Invoice() {
        this.title = "Invoice Template";
        this.content = "Invoice Content...";
    }

    void show() {
        System.out.println("Invoice Title: " + title);
        System.out.println("Invoice Content: " + content);
    }
}

class DocumentCache {

    private static final java.util.Map<String, Document> cache = new java.util.HashMap<>();

    public static void loadCache() {
        cache.put("report", new Report());
        cache.put("invoice", new Invoice());
    }

    public static Document getDocument(String type) {
        Document doc = cache.get(type);
        return doc.clone();
    }
}
```

- Objects are cloned instead of instantiated with new.
- A cache (registry) stores prototype instances.
- The client gets a copy without knowing the concrete class.

#### Conclusion
- Should be used when object creation is expensive
- Improves performance by reusing existing instances
- Reduces the need for subclassing for object creation
- Useful when many similar objects are needed

---

## Structural Patterns

## Adapter
This pattern is a structural pattern.
- Allows incompatible interfaces to work together.
- Acts as a bridge between two different interfaces.
- Converts one interface into another expected by the client.
- Helps reuse existing classes without modifying them.

Eg: Adapting a legacy payment system to a new payment interface.

```java
interface PaymentProcessor {
    void pay(int amount);
}

class LegacyPaymentSystem {
    public void makePayment(int valueInCents) {
        System.out.println("Processing payment of " + valueInCents + " cents using legacy system");
    }
}

class PaymentAdapter implements PaymentProcessor {

    private final LegacyPaymentSystem legacySystem;

    public PaymentAdapter(LegacyPaymentSystem legacySystem) {
        this.legacySystem = legacySystem;
    }

    public void pay(int amount) {
        int valueInCents = amount * 100;
        legacySystem.makePayment(valueInCents);
    }
}
```
- The adapter wraps the existing (legacy) class.
- It converts the expected interface into the compatible one.
- The client interacts only with the target interface.

#### Conclusion
- Should be used when two incompatible interfaces need to work together
- Helps reuse existing or legacy code without modification
- Improves flexibility by introducing a conversion layer
- Common in integrations and external APIs

## Decorator
This pattern is a structural pattern.
- Adds new behavior to objects dynamically.
- Wraps objects instead of modifying their code.
- Follows the Open/Closed Principle (open for extension, closed for modification).
- Allows combining multiple behaviors at runtime.

Eg: Adding multiple notification channels like email and SMS on top of a basic notification.

```java
interface Notification {
    void send(String message);
}

class BasicNotification implements Notification {
    public void send(String message) {
        System.out.println("Sending basic notification: " + message);
    }
}

abstract class NotificationDecorator implements Notification {

    protected final Notification wrapped;

    public NotificationDecorator(Notification wrapped) {
        this.wrapped = wrapped;
    }

    public void send(String message) {
        wrapped.send(message);
    }
}

class EmailDecorator extends NotificationDecorator {

    public EmailDecorator(Notification wrapped) {
        super(wrapped);
    }

    public void send(String message) {
        super.send(message);
        System.out.println("Sending EMAIL notification: " + message);
    }
}

class SMSDecorator extends NotificationDecorator {

    public SMSDecorator(Notification wrapped) {
        super(wrapped);
    }

    public void send(String message) {
        super.send(message);
        System.out.println("Sending SMS notification: " + message);
    }
}
```

- The decorator wraps the original object.
- Each decorator adds new behavior before or after delegating.
- Multiple decorators can be combined dynamically.

#### Conclusion
- Should be used when you need to add responsibilities dynamically
- Avoids creating many subclasses for every combination of features
- Keeps code flexible and extensible
- Common in middleware, logging, and UI composition
