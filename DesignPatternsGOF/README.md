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


### Factory Method
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

### Abstract Factory
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

### Builder
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

### Prototype
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

### Adapter
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

### Decorator
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

### Facade
This pattern is a structural pattern.
- Provides a simplified interface to a complex system.
- Hides the complexity of multiple subsystems.
- Reduces dependencies between client and subsystems.
- Improves readability and usability of the system.

Eg: Placing an order using a single method that internally handles payment, inventory, and shipping.

```java
class PaymentService {
    public void processPayment() {
        System.out.println("Processing payment...");
    }
}

class InventoryService {
    public void checkStock() {
        System.out.println("Checking inventory...");
    }
}

class ShippingService {
    public void shipOrder() {
        System.out.println("Shipping order...");
    }
}

class OrderFacade {

    private final PaymentService paymentService;
    private final InventoryService inventoryService;
    private final ShippingService shippingService;

    public OrderFacade() {
        this.paymentService = new PaymentService();
        this.inventoryService = new InventoryService();
        this.shippingService = new ShippingService();
    }

    public void placeOrder() {
        paymentService.processPayment();
        inventoryService.checkStock();
        shippingService.shipOrder();
        System.out.println("Order placed successfully!");
    }
}
```

- The facade provides a single entry point to multiple subsystems.
- Clients interact only with the facade, not with individual services.
- The internal complexity is hidden from the client.

#### Conclusion
- Should be used when working with complex systems
- Simplifies client interaction with multiple components
- Reduces coupling between client and subsystems
- Improves code organization and readability


### Proxy
This pattern is a structural pattern.
- Provides a placeholder or surrogate for another object.
- Controls access to the original object.
- Adds additional behavior like security, caching, or logging.
- Uses the same interface as the real object.

Eg: Restricting access to a file service based on user role.

```java
interface FileService {
    void readFile(String filename);
}

class RealFileService implements FileService {
    public void readFile(String filename) {
        System.out.println("Reading file: " + filename);
    }
}

class FileServiceProxy implements FileService {

    private final RealFileService realService;
    private final String userRole;

    public FileServiceProxy(String userRole) {
        this.realService = new RealFileService();
        this.userRole = userRole;
    }

    public void readFile(String filename) {

        if ("ADMIN".equalsIgnoreCase(userRole)) {
            realService.readFile(filename);
        } else {
            System.out.println("Access denied. Only ADMIN can read files.");
        }
    }
}
```

- The proxy controls access to the real object.
- It implements the same interface as the real object.
- Additional logic is executed before delegating to the real object.

#### Conclusion
- Should be used when you need to control access to an object
- Useful for security, caching, logging, and lazy initialization
- Keeps the client unaware of the control logic
- Adds flexibility without modifying the original class

---

## Behavioral Patterns

### Strategy
This pattern is a behavioral pattern.
- Defines a family of algorithms and makes them interchangeable.
- Allows behavior to be selected at runtime.
- Encapsulates each algorithm in a separate class.
- Eliminates the need for multiple conditional statements.

Eg: Choosing different payment methods like credit card, PayPal, or Pix dynamically.

```java
interface PaymentStrategy {
    void pay(int amount);
}

class CreditCardPayment implements PaymentStrategy {
    public void pay(int amount) {
        System.out.println("Paid " + amount + " using Credit Card");
    }
}

class PayPalPayment implements PaymentStrategy {
    public void pay(int amount) {
        System.out.println("Paid " + amount + " using PayPal");
    }
}

class PixPayment implements PaymentStrategy {
    public void pay(int amount) {
        System.out.println("Paid " + amount + " using Pix");
    }
}

class PaymentContext {

    private PaymentStrategy strategy;

    public void setStrategy(PaymentStrategy strategy) {
        this.strategy = strategy;
    }

    public void processPayment(int amount) {
        if (strategy == null) {
            throw new IllegalStateException("Payment strategy not set");
        }
        strategy.pay(amount);
    }
}
```

- Each strategy encapsulates a specific algorithm.
- The context delegates execution to the selected strategy.
- Strategies can be changed dynamically at runtime.

#### Conclusion
- Should be used when multiple algorithms can be applied interchangeably
- Reduces complex conditional logic
- Makes code easier to extend and maintain
- Promotes composition over inheritance

### Observer
This pattern is a behavioral pattern.
- Defines a one-to-many dependency between objects.
- When one object changes state, all its dependents are notified automatically.
- Promotes loose coupling between subject and observers.
- Supports event-driven programming.

Eg: Notifying customers and sellers when an order status changes.

```java
import java.util.ArrayList;
import java.util.List;

interface Observer {
    void update(String status);
}

class Customer implements Observer {

    private final String name;

    public Customer(String name) {
        this.name = name;
    }

    public void update(String status) {
        System.out.println(name + " received update: " + status);
    }
}

class Seller implements Observer {

    public void update(String status) {
        System.out.println("Seller notified: " + status);
    }
}

class Order {

    private final List<Observer> observers = new ArrayList<>();
    private String status;

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void setStatus(String status) {
        this.status = status;
        notifyObservers();
    }

    private void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(status);
        }
    }
}
```

- The subject maintains a list of observers.
- Observers subscribe to receive updates.
- When the state changes, all observers are notified.

#### Conclusion
- Should be used when multiple objects depend on a single object’s state
- Enables event-driven architectures
- Reduces tight coupling between components
- Common in UI frameworks, messaging systems, and real-time updates

### Command
This pattern is a behavioral pattern.
- Encapsulates a request as an object.
- Allows parameterization of clients with different requests.
- Decouples the sender from the receiver.
- Supports undo/redo and queueing of requests.

Eg: A remote control triggering different actions like turning a light on or off.

```java
interface Command {
    void execute();
}

class Light {

    public void turnOn() {
        System.out.println("Light is ON");
    }

    public void turnOff() {
        System.out.println("Light is OFF");
    }
}

class TurnOnCommand implements Command {

    private final Light light;

    public TurnOnCommand(Light light) {
        this.light = light;
    }

    public void execute() {
        light.turnOn();
    }
}

class TurnOffCommand implements Command {

    private final Light light;

    public TurnOffCommand(Light light) {
        this.light = light;
    }

    public void execute() {
        light.turnOff();
    }
}

class RemoteControl {

    private Command command;

    public void setCommand(Command command) {
        this.command = command;
    }

    public void pressButton() {
        if (command == null) {
            throw new IllegalStateException("No command set");
        }
        command.execute();
    }
}
```

- The command encapsulates the action and its receiver.
- The invoker triggers the command without knowing the implementation.
- The receiver performs the actual work.

#### Conclusion
- Should be used when you want to decouple sender and receiver
- Useful for implementing undo/redo functionality
- Supports queuing and logging of requests
- Improves flexibility and extensibility of code

### Chain of Responsibility
This pattern is a behavioral pattern.
- Passes a request along a chain of handlers.
- Each handler decides whether to process the request or pass it to the next.
- Decouples sender and receiver of a request.
- Allows multiple objects to handle a request without the client knowing which one will handle it.

Eg: Processing a request through authentication, logging, and validation steps.

```java
abstract class Handler {

    protected Handler next;

    public Handler setNext(Handler next) {
        this.next = next;
        return next;
    }

    public abstract void handle(String request);
}

class AuthHandler extends Handler {

    public void handle(String request) {

        if (!request.contains("auth=true")) {
            System.out.println("Authentication failed");
            return;
        }

        System.out.println("Authentication passed");

        if (next != null) {
            next.handle(request);
        }
    }
}

class LoggingHandler extends Handler {

    public void handle(String request) {
        System.out.println("Logging request: " + request);

        if (next != null) {
            next.handle(request);
        }
    }
}

class ValidationHandler extends Handler {

    public void handle(String request) {

        if (!request.contains("data=")) {
            System.out.println("Validation failed: no data");
            return;
        }

        System.out.println("Validation passed");

        if (next != null) {
            next.handle(request);
        }
    }
}
```
- Each handler processes the request or passes it forward.
- The chain is built dynamically by linking handlers.
- The request flows through the chain until it is handled or stopped.

#### Conclusion
- Should be used when multiple objects can handle a request
- Decouples sender from receiver
- Allows flexible and dynamic request processing pipelines
- Common in middleware, filters, and validation chains