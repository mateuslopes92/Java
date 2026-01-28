import java.util.function.BiFunction;
import java.util.function.Function;

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

    }
}