package org.example.creational;

// Java program implementing Factory Pattern

// Step 1: Create an interface (common type)
interface Shape {
    void draw();
}

// Step 2: Create concrete classes implementing the same interface
class Circle implements Shape {
    @Override
    public void draw() {
        System.out.println("Drawing a Circle");
    }
}

class Square implements Shape {
    @Override
    public void draw() {
        System.out.println("Drawing a Square");
    }
}

class Triangle implements Shape {
    @Override
    public void draw() {
        System.out.println("Drawing a Triangle");
    }
}

// Step 3: Create Factory class
class ShapeFactory {

    // Static method to get object based on input
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

// Main
public class FactoryExample {

    public static void main(String[] args) {

        System.out.println("-------Factory-------");

        // Instead of: new Circle(), new Square()...
        Shape shape1 = ShapeFactory.getShape("circle");
        Shape shape2 = ShapeFactory.getShape("square");
        Shape shape3 = ShapeFactory.getShape("triangle");

        shape1.draw();
        shape2.draw();
        shape3.draw();

        System.out.println("---------------------\n");
    }
}