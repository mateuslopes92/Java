//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Inventory inventory = new Inventory();

        Item genericItem = new Item("Macbook",2);
        Fruit fruit = new Fruit("Apple",10, "Fuji");

        inventory.addItem(genericItem);
        inventory.addItem(fruit);

        inventory.displayInventory();
    }
}