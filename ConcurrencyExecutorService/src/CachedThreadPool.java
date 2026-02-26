import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CachedThreadPool {
    public void execute(){
        // For a lot of short-lived tasks
        // Does not have a fixed number of threads; can create new threads as needed.
        // Uses a synchronous queue that holds only one task at a time.
        // Threads that are idle for more than 60 seconds are terminated to save resources.
        try (ExecutorService service = Executors.newCachedThreadPool()) {

            // Submit tasks for execution
            for (int i = 0; i < 100; i++) {
                // short-lived task
                service.execute(new Task());
            }
        }
    }
}
