import java.util.List;
import java.util.Map;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
         * - filter() receives a Predicate (Car -> boolean)
         * - Only elements that return true are kept in the stream
         * - Here: keep only cars whose type is "sedan"
         */
        System.out.println("--------Filter--------");
        Predicate<Car> isSedan = car -> car.type().equals("sedan");
        List<Car> sedanCars = cars.stream().filter(isSedan).toList();
        System.out.println("Filtering elements with stream(sedan cars): " + sedanCars);
        System.out.println("----------------------");

        System.out.println();

        /**
         * Map Function
         * - map() transforms each element into another value
         * - One input produces exactly one output
         * - Here: Car -> make (String)
         */
        System.out.println("--------Map--------");
        List<String> carMakeList = cars.stream().map(car -> car.make).toList();
        System.out.println("Mapping througth elements with streams(makes cars): " + carMakeList);
        System.out.println("----------------------");

        System.out.println();

        /**
         * Flat Map Function
         * - flatMap() maps each element to a Stream
         * - then flattens all streams into a single stream
         * - Used when one element produces multiple values
         * - Here: Car -> Stream(make, model)
         */
        System.out.println("--------Flat Map--------");
        // Make followed by model -> Audi, A5, BMW, 320
        List<String> carMakeModelList = cars.stream().flatMap(car -> Stream.of(car.make, car.model)).toList();
        System.out.println("Flat Mapping througth elements with streams(makes and model cars): " + carMakeModelList);
        System.out.println("----------------------");

        System.out.println();

        /**
         * Partitioning by Collector
         * This return a map with true and false values with his data
         */
        System.out.println("--------Partitioning by Collector--------");
        Map<Boolean, List<Car>> partitionedCars = cars.stream().collect(Collectors.partitioningBy(car -> car.type().equals("sedan")));
        System.out.println("Cars partitioned by collector: " + partitionedCars);
        System.out.println("----------------------");

        System.out.println();

        /**
         * Grouping by Collector
         *
         */
        System.out.println("--------Grouping by Collector--------");
        // (type (make, engineCapacity))
        Map<String, Map<String, Integer>> groupedCars = cars
                .stream()
                .collect(Collectors.groupingBy(
                        Car::type,
                        Collectors.toMap(
                                Car::make,
                                Car::engineCapacity
                        )
                        )
                );
        System.out.println("Cars grouped by collector: " + groupedCars);
        System.out.println("----------------------");

        System.out.println();

        /**
         * Parallel Streams + Consumer
         * - Parallel by default is false, so we can use parallelStream
         * - Execute not only in main thread but in other threads
         * - To model the transformation we can use `.sequential()` that removes parallel support to the operation
         * - And to `.parallel()` which enables on the fly if have a sequential stream
         */
        System.out.println("--------Parallel Streams--------");
        // Consumer<Car> → takes a Car, returns nothing
        Consumer<Car> printCar = car ->
                System.out.println("Running parallel streams: Consuming car: " + car);
        cars.parallelStream().forEach(printCar);
        System.out.println("----------------------");

        System.out.println();

        /**
         * Supplier with streams
         */
        System.out.println("--------Supplier--------");
        // Supplier<Car> → produces a Car, no input
        Supplier<Car> carSupplier = () ->
                new Car("sedan", "Honda", "Civic", 2000);
        Car suppliedCar = carSupplier.get();
        System.out.println("Car supplied by Supplier: " + suppliedCar);
        System.out.println("------------------------");

        System.out.println();

        /**
         * UnaryOperator
         */
        System.out.println("--------UnaryOperator--------");
        // UnaryOperator<Car> → Car in, Car out
        UnaryOperator<Car> upgradeEngine = car ->
                new Car(
                        car.type(),
                        car.make(),
                        car.model(),
                        car.engineCapacity() + 200
                );
        List<Car> upgradedCars = cars.stream()
                .map(upgradeEngine)
                .toList();
        System.out.println("Cars after engine upgrade with unary operator: " + upgradedCars);
        System.out.println("-----------------------------");

        System.out.println();

        System.out.println("--------BinaryOperator--------");
        // BinaryOperator<Integer> → combine two Integers into one
        BinaryOperator<Integer> sumEngines = Integer::sum;
        int totalEngineCapacity = cars.stream()
                .map(Car::engineCapacity)
                .reduce(0, sumEngines);
        System.out.println("Total engine capacity: " + totalEngineCapacity);
        System.out.println("------------------------------");
    }
}