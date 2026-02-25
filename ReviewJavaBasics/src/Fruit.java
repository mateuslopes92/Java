public class Fruit implements ItemStuff {
    private int quantity;
    private String name;
    private String type;

    public Fruit(String name, int quantity, String type) {
        this.name = name;
        this.quantity = quantity;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    /**
     * Runtime polymorphism(dynamic) using Override
     * @return String
     */
    @Override
    public void displayInfo(){
        System.out.println("Fruit: " + getName() + ", Quantity: " + getQuantity() + ", Type: " + type);
    }
}
