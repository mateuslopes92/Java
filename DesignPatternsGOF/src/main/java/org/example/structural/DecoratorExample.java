package org.example.structural;

// Step 1: Component interface
interface Notification {
    void send(String message);
}

// Step 2: Concrete Component
class BasicNotification implements Notification {
    @Override
    public void send(String message) {
        System.out.println("Sending basic notification: " + message);
    }
}

// Step 3: Base Decorator
abstract class NotificationDecorator implements Notification {

    protected final Notification wrapped;

    public NotificationDecorator(Notification wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public void send(String message) {
        wrapped.send(message);
    }
}

// Step 4: Concrete Decorators
class EmailDecorator extends NotificationDecorator {

    public EmailDecorator(Notification wrapped) {
        super(wrapped);
    }

    @Override
    public void send(String message) {
        super.send(message);
        System.out.println("Sending EMAIL notification: " + message);
    }
}

class SMSDecorator extends NotificationDecorator {

    public SMSDecorator(Notification wrapped) {
        super(wrapped);
    }

    @Override
    public void send(String message) {
        super.send(message);
        System.out.println("Sending SMS notification: " + message);
    }
}

// Main
public class DecoratorExample {

    public static void main(String[] args) {

        System.out.println("-------Decorator-------");

        // Basic notification
        Notification notification = new BasicNotification();

        // Add Email behavior
        notification = new EmailDecorator(notification);

        // Add SMS behavior
        notification = new SMSDecorator(notification);

        // Execute all behaviors
        notification.send("Hello World!");

        System.out.println("-----------------------\n");
    }
}
