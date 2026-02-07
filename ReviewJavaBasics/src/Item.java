public abstract class Item {
    private String name;
    private int quantity;

    public Item(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    /**
     * Runtime polymorphism(dynamic) using Override
     * this was used to test polymorphism
     * @return String
     */
//    @Override
//    public String toString(){
//        return "Item: " + name + ", Quantity: " + quantity;
//    }

    public abstract void displayInfo();
}
