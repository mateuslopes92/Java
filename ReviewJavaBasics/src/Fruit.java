public class Fruit extends Item {
    private String type;

    public Fruit(String name, int quantity, String type) {
        super(name, quantity);
        this.type = type;
    }

    public String getType() {
        return type;
    }

    /**
     * Runtime polymorphism(dynamic) using Override
     * @return String
     */
    @Override
    public String toString(){
        return "Fruit: " + getName() + ", Quantity: " + getQuantity() + ", Type: " + type;
    }
}
