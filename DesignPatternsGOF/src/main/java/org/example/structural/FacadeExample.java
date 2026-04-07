package org.example.structural;

// Subsystem 1
class PaymentService {
    public void processPayment() {
        System.out.println("Processing payment...");
    }
}

// Subsystem 2
class InventoryService {
    public void checkStock() {
        System.out.println("Checking inventory...");
    }
}

// Subsystem 3
class ShippingService {
    public void shipOrder() {
        System.out.println("Shipping order...");
    }
}

// Facade
class OrderFacade {

    private final PaymentService paymentService;
    private final InventoryService inventoryService;
    private final ShippingService shippingService;

    public OrderFacade() {
        this.paymentService = new PaymentService();
        this.inventoryService = new InventoryService();
        this.shippingService = new ShippingService();
    }

    // Simplified method
    public void placeOrder() {
        paymentService.processPayment();
        inventoryService.checkStock();
        shippingService.shipOrder();
        System.out.println("Order placed successfully!");
    }
}

// Main
public class FacadeExample {

    public static void main(String[] args) {

        System.out.println("-------Facade-------");

        // Client interacts only with facade
        OrderFacade orderFacade = new OrderFacade();
        orderFacade.placeOrder();

        System.out.println("--------------------\n");
    }
}