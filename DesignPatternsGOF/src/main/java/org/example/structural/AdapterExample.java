package org.example.structural;

// Step 1: Target interface (what client expects)
interface PaymentProcessor {
    void pay(int amount);
}

// Step 2: Adaptee (existing/legacy system)
class LegacyPaymentSystem {
    public void makePayment(int valueInCents) {
        System.out.println("Processing payment of " + valueInCents + " cents using legacy system");
    }
}

// Step 3: Adapter (converts interface)
class PaymentAdapter implements PaymentProcessor {

    private final LegacyPaymentSystem legacySystem;

    public PaymentAdapter(LegacyPaymentSystem legacySystem) {
        this.legacySystem = legacySystem;
    }

    @Override
    public void pay(int amount) {
        // Convert dollars to cents
        int valueInCents = amount * 100;
        legacySystem.makePayment(valueInCents);
    }
}

// Main
public class AdapterExample {

    public static void main(String[] args) {

        System.out.println("-------Adapter-------");

        // Using adapter to connect new interface with legacy system
        PaymentProcessor processor = new PaymentAdapter(new LegacyPaymentSystem());

        processor.pay(50); // $50 → converted to cents

        System.out.println("---------------------\n");
    }
}