import java.util.concurrent.*;

public class RejectorExecution {
    public void execute(){
        try (ExecutorService service = new ThreadPoolExecutor(
                10,
                100,
                120,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(300)
        )){
            for(int i = 0; i < 500; i++){
                service.execute(new Task());
            }
        } catch (RejectedExecutionException e){
            System.err.println("Task rejected " + e.getMessage());
        }
    }
}
