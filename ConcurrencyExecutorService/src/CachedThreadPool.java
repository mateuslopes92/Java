import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CachedThreadPool {
    public void execute(){
        // For a lot of short-lived tasks
        try (ExecutorService service = Executors.newCachedThreadPool()) {

            // Submit tasks for execution
            for (int i = 0; i < 100; i++) {
                // short-lived task
                service.execute(new Task());
            }
        }
    }
}
