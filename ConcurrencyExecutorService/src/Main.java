import java.util.concurrent.ScheduledExecutorService;

/**
 * Types of threads(4 basics)
 * FixedThreadPool (Fixed number of threads(safe - blocking queue)
 * CachedThreadPool (Not have fixed number, not have queue, have synchronous queue), uses free threads or create a thread if dont have a free one, kill threads idle when more than 60 seconds wth no execution
 * ScheduledThreadPool (Tasks that want to schedule after certain a delay/rate) more threads area created if required - Uses a delay queue
 * SingleThreadExecutor (Same as fixedThreadPool, but size is only one thread) recreates if is killed because of the task, to ensure one task run at time
 */

public class Main {
    public static void main(String[] args) throws InterruptedException {
        // FixedThreadPool
//        FixedThreadPool runFixedThreadPool = new FixedThreadPool();
//        runFixedThreadPool.execute();

        // CachedThreadPool
//        CachedThreadPool cachedThreadPool = new CachedThreadPool();
//        cachedThreadPool.execute();

        // ScheduledThreadPool
//        ScheduledThreadPool scheduledThreadPool = new ScheduledThreadPool();
        // task that needs to run
        // based on the schedule
//        scheduledThreadPool.execute();

        // SingleThreadExecutor
//        MySingleThreadExecutor mySingleThreadExecutor = new MySingleThreadExecutor();
//        mySingleThreadExecutor.execute();

        // RejectedExecutionException
//        RejectorExecution rejectorExecution = new RejectorExecution();
//        rejectorExecution.execute();

        // ExecutorShutdown
//        ExecutorShutdown.runExample();

        // Callable
        CallableExample.run();
    }
}