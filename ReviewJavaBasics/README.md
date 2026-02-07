# OOP review study

## 1. Encapsulation
   - Definition
     - Both ways to achieve polymorphism are also part of encapsulation.
     - Encapsultaion is all about bundling the data in the attributes as well as the methods into a single unit

## 2. Inheritance
   - Definition
     - Inheritance allows a new class (subclass) to inherit attributes and methods from an existing class (superclass).
   - Implementation
     - Use the extends keyword to create a subclass.
     - Example: A Fruit class can extend an Item class, inheriting its attributes while adding specific attributes like type.
## 3. Polymorphism
   - Definition
   Polymorphism allows methods to do different things based on the object that invokes them. It can be achieved through method overriding and overloading.
   Method overloading allows methods to have the same name but with different parameters static(compile time)
   Compile time with overloading allows us to get errors soon, doenst run in runtime when with errors, also increse performance
   Runtime Override run in runtime, is more dynamic.
   - Implementation
     - Method Overriding
     Subclasses can provide specific implementations of methods defined in their superclass.
   - Method Overloading
     - Multiple methods can have the same name but different parameters.
     - Example: The toString() method can be overridden in subclasses to provide specific string representations.
## 4. Abstraction
   - Definition
   Abstraction is the concept of hiding complex implementation details and showing only the essential features of an object.
   - Implementation
     - Use abstract classes and methods to define a template for subclasses.
     - Interfaces can be used to define methods that must be implemented by any class that implements the interface.
     - Example: An abstract Item class can define an abstract method displayInfo() that subclasses must implement.