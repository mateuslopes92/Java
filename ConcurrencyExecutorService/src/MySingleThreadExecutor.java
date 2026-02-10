import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MySingleThreadExecutor {

    public void execute() {
        // Creates an executor that uses a single worker thread
        // Tasks are executed sequentially, one at a time
        try (ExecutorService service = Executors.newSingleThreadExecutor()) {

            // Submit multiple tasks
            for (int i = 0; i < 10; i++) {
                int taskId = i;

                service.execute(() -> {
                    System.out.println(
                            "Task " + taskId +
                                    " executed by " + Thread.currentThread().getName()
                    );

                    try {
                        Thread.sleep(500); // Simulate work
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                });
            }
        }
    }
}