//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Inventory inventory = new Inventory();

        Item macbook = new Item(
    "Macbook",
    2
        );
        Item iPhone = new Item(
    "iPhone 16 Pro Max",
    1
        );

        inventory.addItem(macbook);
        inventory.addItem(iPhone);

        inventory.displayInventory();
    }
}