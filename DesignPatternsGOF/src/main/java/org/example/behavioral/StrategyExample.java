package org.example.behavioral;

// Step 1: Strategy interface
interface PaymentStrategy {
    void pay(int amount);
}

// Step 2: Concrete strategies

class CreditCardPayment implements PaymentStrategy {
    @Override
    public void pay(int amount) {
        System.out.println("Paid " + amount + " using Credit Card");
    }
}

class PayPalPayment implements PaymentStrategy {
    @Override
    public void pay(int amount) {
        System.out.println("Paid " + amount + " using PayPal");
    }
}

class PixPayment implements PaymentStrategy {
    @Override
    public void pay(int amount) {
        System.out.println("Paid " + amount + " using Pix");
    }
}

// Step 3: Context (uses a strategy)
class PaymentContext {

    private PaymentStrategy strategy;

    public void setStrategy(PaymentStrategy strategy) {
        this.strategy = strategy;
    }

    public void processPayment(int amount) {
        if (strategy == null) {
            throw new IllegalStateException("Payment strategy not set");
        }
        strategy.pay(amount);
    }
}

// Main
public class StrategyExample {

    public static void main(String[] args) {

        System.out.println("-------Strategy-------");

        PaymentContext context = new PaymentContext();

        // Use Credit Card
        context.setStrategy(new CreditCardPayment());
        context.processPayment(100);

        // Switch to PayPal
        context.setStrategy(new PayPalPayment());
        context.processPayment(200);

        // Switch to Pix
        context.setStrategy(new PixPayment());
        context.processPayment(300);

        System.out.println("----------------------\n");
    }
}
