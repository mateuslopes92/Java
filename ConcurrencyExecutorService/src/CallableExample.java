import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CallableExample {
    // Create the pool
    private static ExecutorService serivce = Executors.newFixedThreadPool(10);

    public static void run(){
        // Submit the tasks for execution
        for (int i =0; i < 100; i++){
            serivce.submit(new CallableTask());
        }
        System.out.println("Thread name: " + Thread.currentThread().getName());
    }
}
