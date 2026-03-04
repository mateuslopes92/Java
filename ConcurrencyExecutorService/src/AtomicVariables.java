import java.util.concurrent.atomic.AtomicInteger;

public class AtomicVariables {

    // Shared atomic variable (no synchronized, no Lock needed)
    // AtomicInteger guarantees thread-safe operations using CAS (Compare-And-Swap)
    private final AtomicInteger counter = new AtomicInteger(0);

    // Multiple threads can execute this method at the same time
    // There is NO locking here
    // Thread-safety is guaranteed by AtomicInteger
    public void increment() {

        System.out.println(
                Thread.currentThread().getName()
                        + " started incrementing the counter.");

        // Simulate some work before heavy increment
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        int max = 100_000_000; // 100 million increments per thread
        int value = 0;

        // Both threads compete for the SAME atomic counter
        // Each increment is atomic (lock-free)
        for (int i = 0; i < max; i++) {

            // Atomic operation (internally uses CAS)
            value = counter.incrementAndGet();
        }

        System.out.println(
                Thread.currentThread().getName()
                        + " finished. Last observed value: " + value);
    }

    // Getter method to retrieve the final counter value
    public int getCount() {
        return this.counter.get();
    }

    public static void main(String[] args) throws InterruptedException {

        // Single shared object (shared resource)
        AtomicVariables atmV = new AtomicVariables();

        // Two threads competing for the same AtomicInteger
        Thread t1 = new Thread(atmV::increment, "Thread-1");
        Thread t2 = new Thread(atmV::increment, "Thread-2");

        t1.start(); // Thread-1 starts incrementing
        t2.start(); // Thread-2 starts incrementing concurrently

        // Wait for both threads to finish
        t1.join();
        t2.join();

        // Expected final value:
        // 100,000,000 * 2 = 200,000,000
        System.out.println("\nFINAL VALUE => " + atmV.getCount());
    }
}