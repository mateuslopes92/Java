import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPool {
    public void execute(){
        // for scheduling of tasks
        try(ScheduledExecutorService service = Executors.newScheduledThreadPool(10)){
            // task to run after 10 seconds delay
            service.schedule(new Task(), 10, TimeUnit.SECONDS);

            // task to run repeatedly every 10 seconds
            // service.scheduleAtFixedRate(new Task(), 15, 10, TimeUnit.SECONDS);

            // task to run repeatedly 10 seconds after previous task completes
            // service.scheduleWithFixedDelay(new Task(), 15, 10, TimeUnit.SECONDS);
        }
    }
}
