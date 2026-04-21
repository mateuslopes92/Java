package org.example;

public class PatternMatchingExample {

    public static void main(String[] args) {

        System.out.println("-------Pattern Matching-------");

        Object obj1 = "Hello World";
        Object obj2 = 123;
        Object obj3 = null;

        // Example 1: Pattern matching with instanceof
        // Old Java required casting after instanceof check
        // Now we can declare the variable directly in the condition
        if (obj1 instanceof String s) {
            System.out.println("String length: " + s.length());
        }

        if (obj2 instanceof Integer i) {
            System.out.println("Integer value: " + i);
        }

        // Example 2: Pattern matching with switch
        // Cleaner and more expressive than old switch
        System.out.println(format(obj1));
        System.out.println(format(obj2));
        System.out.println(format(obj3));

        System.out.println("--------------------------------\n");
    }

    // Method using pattern matching in switch
    public static String format(Object obj) {
        return switch (obj) {

            case String s -> "This is a String: " + s;

            case Integer i -> "This is an Integer: " + i;

            case null -> "Value is null";

            default -> "Unknown type";
        };
    }
}
