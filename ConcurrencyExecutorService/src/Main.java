import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {

        // Example of simple task running with a poolsize based on available processors
        // Get count of available cores (eg. 4 cores in a processor)
        int coreCount = Runtime.getRuntime().availableProcessors();

        // CPU intensive - pool size ideal is CPU core count - other apps may use same CPU
        // IO Intensive(Database, Network) - pool size ideal is higher - Exact number depends on rate of task submissions and average task wait time
        // IO -> too many threads will increase memory consumption
        try (ExecutorService service = Executors.newFixedThreadPool(coreCount)) {
            // Submit the tasks for execution
            for (int i = 0; i < 100; i++) {
                service.execute(new CpuIntensiveTask());
            }
        }
    }
}