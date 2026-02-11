import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableExample {
    // Create the pool
    private static ExecutorService serivce = Executors.newFixedThreadPool(10);

    public static void run(){
        // Submit the tasks for execution
        Future<Integer> future = serivce.submit(new CallableTask());

        // Perform some unrelated operations

        // 3 Sec
        Integer result = null; // blocking
        try {
            result = future.get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Thread name: " + Thread.currentThread().getName() + " Task return: " + result);
    }
}
