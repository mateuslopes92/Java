package org.example.behavioral;

// Step 1: Handler abstract class
abstract class Handler {

    protected Handler next;

    public Handler setNext(Handler next) {
        this.next = next;
        return next;
    }

    public abstract void handle(String request);
}

// Step 2: Concrete Handlers

class AuthHandler extends Handler {

    @Override
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

    @Override
    public void handle(String request) {
        System.out.println("Logging request: " + request);

        if (next != null) {
            next.handle(request);
        }
    }
}

class ValidationHandler extends Handler {

    @Override
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

// Main
public class ChainOfResponsibilityExample {

    public static void main(String[] args) {

        System.out.println("-------Chain of Responsibility-------");

        // Build chain: Auth → Logging → Validation
        Handler auth = new AuthHandler();
        Handler logging = new LoggingHandler();
        Handler validation = new ValidationHandler();

        auth.setNext(logging).setNext(validation);

        // Request
        String request = "auth=true;data=hello";

        auth.handle(request);

        System.out.println("------------------------------------\n");
    }
}