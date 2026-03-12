@VeryImportant
public class Dog {

    @ImportantString
    String name;

    int age;

    public Dog(String name){
        this.name = name;
    }

//    @VeryImportant
    @RunImmediately(times = 3)
    public void bark(){
        System.out.println("Ruf!");
    }

    public void eat(){
        System.out.println("Munch");
    }
}
