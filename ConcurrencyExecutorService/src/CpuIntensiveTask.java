
// With this class implementing runnable we can create a thread
// This can be used like: Thread thread1 = new Thread(new Task());
// thread1.start()
public class CpuIntensiveTask implements Runnable {
    @Override
    public void run() {
        // Some CPU intensive operations
        System.out.println("Intensive operation running");
    }
}
