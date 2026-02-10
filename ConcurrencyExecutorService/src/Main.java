/**
 * Types of threads(4 basics)
 * FixedThreadPool (Fixed number of threads(safe - blocking queue)
 * CachedThreadPool (Not have fixed number, not have queue, have synchronous queue), uses free threads or create a thread if dont have a free one, kill threads idle when more than 60 seconds wth no execution
 * ScheduledThreadPool (Tasks that want to schedule after certain a delay/rate) more threads area created if required - Uses a delay queue
 * SingleThreadExecutor
 */

public class Main {
    public static void main(String[] args) {
        // FixedThreadPool
        FixedThreadPool runFixedThreadPool = new FixedThreadPool();
        runFixedThreadPool.execute();

        // CachedThreadPool
        CachedThreadPool cachedThreadPool = new CachedThreadPool();
        cachedThreadPool.execute();
    }
}