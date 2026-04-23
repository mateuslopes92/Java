package org.example;

// How to restrict which classes can extend or implement a type
public class SealedClassesExample {

    // Define a sealed interface
    // Only the permitted classes can implement this interface
    sealed interface Payment permits CreditCard, Pix, Cash {}

    // Define permitted classes
    // final = cannot be extended further
    static final class CreditCard implements Payment {}

    static final class Pix implements Payment {}

    // non-sealed = can be extended by other classes
    static non-sealed class Cash implements Payment {}

    // Example of extending a non-sealed class
    static class SpecialCash extends Cash {}

    public static void main(String[] args) {

        System.out.println("-------Sealed Classes-------");

        Payment p1 = new CreditCard();
        Payment p2 = new Pix();
        Payment p3 = new Cash();

        // Example: using sealed classes with switch (pattern matching)
        System.out.println(processPayment(p1));
        System.out.println(processPayment(p2));
        System.out.println(processPayment(p3));

        System.out.println("----------------------------\n");
    }

    public static String processPayment(Payment payment) {

        // Because Payment is sealed, the compiler knows all possible types
        return switch (payment) {

            case CreditCard c -> "Processing Credit Card payment";

            case Pix p -> "Processing Pix payment";

            case Cash c -> "Processing Cash payment";
        };
    }
}