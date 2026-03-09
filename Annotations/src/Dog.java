@VeryImportant
public class Dog {
    String name;
    int age;

    public Dog(String name){
        this.name = name;
    }

    @VeryImportant
    public void bark(){
        System.out.println("Ruf!");
    }

    public void eat(){
        System.out.println("Munch");
    }
}
