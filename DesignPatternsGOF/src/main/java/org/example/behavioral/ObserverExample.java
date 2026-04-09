package org.example.behavioral;

import java.util.ArrayList;
import java.util.List;

// Step 1: Observer interface
interface Observer {
    void update(String status);
}

// Step 2: Concrete Observers
class Customer implements Observer {

    private final String name;

    public Customer(String name) {
        this.name = name;
    }

    @Override
    public void update(String status) {
        System.out.println(name + " received update: " + status);
    }
}

class Seller implements Observer {

    @Override
    public void update(String status) {
        System.out.println("Seller notified: " + status);
    }
}

// Step 3: Subject (Observable)
class Order {

    private final List<Observer> observers = new ArrayList<>();
    private String status;

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void setStatus(String status) {
        this.status = status;
        notifyObservers();
    }

    private void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(status);
        }
    }
}

// Main
public class ObserverExample {

    public static void main(String[] args) {

        System.out.println("-------Observer-------");

        Order order = new Order();

        // Create observers
        Observer customer1 = new Customer("Mateus");
        Observer customer2 = new Customer("John");
        Observer seller = new Seller();

        // Subscribe observers
        order.addObserver(customer1);
        order.addObserver(customer2);
        order.addObserver(seller);

        // Change state → notify all
        order.setStatus("Order placed");
        System.out.println();

        order.setStatus("Order shipped");

        System.out.println("----------------------\n");
    }
}