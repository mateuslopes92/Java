package org.example;

// How to extract data directly from record objects
public class RecordPatternExample {

    // Define a record
    // Records are simple data carriers (like DTOs)
    record User(String name, int age) {}

    // Method using record patterns inside switch
    public static String format(Object obj) {
        return switch (obj) {

            // Destructure the record directly
            case User(String name, int age) ->
                    "User -> name: " + name + ", age: " + age;

            default ->
                    "Unknown type";
        };
    }

    public static void main(String[] args) {

        System.out.println("-------Record Patterns-------");

        Object obj1 = new User("Mateus", 25);
        Object obj2 = new User("John", 30);
        Object obj3 = "Not a user";

        // Example 1: Pattern matching with record destructuring (instanceof)
        if (obj1 instanceof User(String name, int age)) {
            System.out.println("User name: " + name);
            System.out.println("User age: " + age);
        }

        System.out.println();

        // Example 2: Using record patterns with switch
        System.out.println(format(obj1));
        System.out.println(format(obj2));
        System.out.println(format(obj3));

        System.out.println("--------------------------------\n");
    }
}
