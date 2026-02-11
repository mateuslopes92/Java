import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorShutdown {
    public static void runExample() throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 100; i++) {
            service.execute(() -> {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        // initiate shutdown
        service.shutdown();

        System.out.println("isShutdown: " + service.isShutdown());
        System.out.println("isTerminated: " + service.isTerminated());

        // wait for termination
        if (!service.awaitTermination(10, TimeUnit.SECONDS)) {
            System.out.println("Timeout reached, forcing shutdown...");
            List<Runnable> pending = service.shutdownNow();
            System.out.println("Pending tasks: " + pending.size());
        }

        System.out.println("Final isTerminated: " + service.isTerminated());
    }
}
