import java.util.function.*;

public class Main {
    public static void main(String[] args) {
//        MyFunctionalInterface mfi = new MyFunctionalInterface() {
//            @Override
//            public void run() {
//                System.out.println("Running method");
//            }
//        };
//
//        mfi.run();

        // implementing in simple way the run function
        MyFunctionalInterface mfi = () -> System.out.println("Hello lambda");
        mfi.run();

        /**
         * Types of functional interfaces from java.util.function*
         */
        //Runnable dont receives parameter and dont return nothing
        Runnable rnb = () -> System.out.println("Runnable: no params no return");
        rnb.run();

        // Function: receives a parameter type and return type
        // Can be any type: Function<Integer, String> test = x -> "Test is " + x;
        Function<Integer, Integer> fnc = x -> x*2;
        Integer fncResult = fnc.apply(2);
        System.out.println("Function: receives a parameter and return the defined type: " + fncResult);

        // BiFunction: receives 2 parameters and return one
        BiFunction<Integer, Integer, Integer> biFnc = (x, y) -> x+y;
        Integer biFncResult = biFnc.apply(2, 8);
        System.out.println("BiFunction: two parameters and return one" + "2 + 8 = " +  biFncResult);

        // IntFunction: receives an integer(Ommited) and return any type
        IntFunction<String> iFnc = x -> "IntFunction: receives an int and return anything, my int: " + x;
        String iFncResult = iFnc.apply(5);
        System.out.println(iFncResult);

        // Supplier: dont receive nothing and return something
        Supplier<Integer> spl = () -> 2;
        Integer splResult = spl.get();
        System.out.println("Supplier: dont receive nothing and return something -> " + splResult);

        // Consumer: receive something and return nothing
        Consumer<Integer> csm = x -> System.out.println("Consumer: receive something and return nothing " + x);
        csm.accept(6);

        // Predicate: receives something and return a boolean
        Predicate<Integer> pdc = x -> x % 2 == 0;
        System.out.println("Predicate: receives something and return boolean: 2 is even: " + pdc.test(2));

    }
}