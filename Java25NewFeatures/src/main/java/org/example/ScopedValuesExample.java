package org.example;

import java.lang.ScopedValue;

// ScopedValue is a safer alternative to ThreadLocal
public class ScopedValuesExample {

    // Create a ScopedValue
    // This acts like a variable that can be shared inside a scope
    private static final ScopedValue<String> USER = ScopedValue.newInstance();

    public static void main(String[] args) {

        System.out.println("-------Scoped Values-------");

        // Define a scope and assign a value
        // The value is only available inside this block
        ScopedValue.where(USER, "Mateus").run(() -> {

            // Inside the scope, we can access USER
            methodA();
            methodB();
        });

        // Outside the scope, USER is not available
        System.out.println("Outside scope: cannot access USER");

        System.out.println("---------------------------\n");
    }

    public static void methodA() {
        // Access the value inside the scope
        System.out.println("Method A sees user: " + USER.get());
    }

    public static void methodB() {
        // Access the value inside the scope
        System.out.println("Method B sees user: " + USER.get());
    }
}
