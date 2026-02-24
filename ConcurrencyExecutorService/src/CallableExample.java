import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableExample {
    // Create the pool
    private static ExecutorService serivce = Executors.newFixedThreadPool(10);

    public static void run(){
        // Submit the tasks for execution
        List<Future> allFutures = new ArrayList<>();
        for(int i = 0; i < 100; i++){
            Future<Integer> future = serivce.submit(new CallableTask());
            allFutures.add(future);
        }
        // 100 futures, 100 placeholders


        // Perform some unrelated operations

        // 100 seconds
        for(int i = 0; i < 100; i++){
            Future<Integer> future = allFutures.get(i);
            Integer result = null;
            try {
                result = future.get();  // blocking
                System.out.println("Result of future #" + i + "=" + result);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
