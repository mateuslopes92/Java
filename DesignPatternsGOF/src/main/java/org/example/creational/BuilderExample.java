package org.example.creational;

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

        // Setters for optional fields (return Builder for chaining)
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

    // Method to display user info
    public void showUser() {
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("Email: " + email);
        System.out.println("Phone: " + phone);
    }
}

// Main
public class BuilderExample {

    public static void main(String[] args) {

        System.out.println("-------Builder-------");

        // Creating object step by step
        User user1 = new User.Builder("Mateus")
                .age(25)
                .email("mateus@email.com")
                .build();

        User user2 = new User.Builder("John")
                .phone("123-456")
                .build();

        user1.showUser();
        System.out.println();
        user2.showUser();

        System.out.println("---------------------\n");
    }
}
