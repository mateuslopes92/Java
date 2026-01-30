import java.util.List;

public class Main {
    record Car(String type, String make, String model, Integer engineCapacity){}

    public static void main(String[] args) {
        List<Car> cars = List.of(
                new Car("sedan", "BMW", "320", 1998),
                new Car("sedan", "Audi", "A5", 1998),
                new Car("sedan", "Mercedes", "E-Class", 2500),
                new Car("hatchback", "Toyota", "Octavia", 1600),
                new Car("sedan", "WV", "Jetta", 2013)
        );

        /**
         * Filtering Elements
         */
        System.out.println("--------Filter--------");
        List<Car> sedanCars = cars.stream().filter(car -> car.type.equals("sedan")).toList();
        System.out.println("Filtering elements with stream(sedan cars): " + sedanCars);
        System.out.println("----------------------");

        System.out.println();

        /**
         * Map Function
         */
        System.out.println("--------Map--------");
        List<String> carMakeList = cars.stream().map(car -> car.make).toList();
        System.out.println("Mapping througth elements with streams(makes cars): " + carMakeList);
        System.out.println("----------------------");


    }
}