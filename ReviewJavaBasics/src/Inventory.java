import java.util.ArrayList;

public class Inventory {
    private ArrayList<Item> items;

    public Inventory(){
        items = new ArrayList<Item>();
    }

    public void addItem(Item item){
        items.add(item);
    }

    // Using overload as an example to polymorphism in compile time
    public void addItem(String name, int quantity, String type){
        items.add(new Fruit(name, quantity, type));
    }

    // Using overload as an example to polymorphism in compile time
    public void addItem(String name, int quantity, int damage, String type){
        items.add(new Weapon(name, quantity, damage, type));
    }


    public void displayInventory(){
        for(Item item : items){
            item.displayInfo();
        }
    }

    public void displayInventory(String type){
        for(Item item : items){
            if(item instanceof Fruit && ((Fruit) item).getType().equalsIgnoreCase(type)){
                item.displayInfo();
            } else if (item instanceof Weapon && ((Weapon) item).getType().equalsIgnoreCase((type))){
                item.displayInfo();
            }
        }
    }
}
