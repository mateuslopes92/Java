package org.example;


// String Templates allow easier string interpolation
public class StringTemplatesExample {

    public static void main(String[] args) {
        System.out.println("-------String Templates-------");

        String name = "Mateus";
        int age = 25;

        // Old way using concatenation
        String oldMessage = "Name: " + name + ", Age: " + age;

        // New way using String Templates
        String newMessage = STR."Name: \{name}, Age: \{age}";

        System.out.println("Old message:");
        System.out.println(oldMessage);

        System.out.println();

        System.out.println("New message:");
        System.out.println(newMessage);

        System.out.println("--------------------------------\n");
    }
}
