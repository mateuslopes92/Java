import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockExample {
    // the ReentrantLock is a lock which if a thread has locked the lock
    // can lock again and again
    // once the thread finishes need to unlock the reentrant lock instance as many times as it locked it
    private static final Lock lock = new ReentrantLock(); // accept true as parameter to be fair(maintain the order)

    public static void main(String[] args) {

        Runnable task = () -> {
            if (lock.tryLock()) {
                try {
                    System.out.println(Thread.currentThread().getName() + " acquired lock");
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    System.out.println(Thread.currentThread().getName() + " released lock");
                    lock.unlock();
                }
            } else {
                System.out.println(Thread.currentThread().getName() + " could NOT acquire lock");
            }
        };

        new Thread(task, "Thread-1").start();
        new Thread(task, "Thread-2").start();
    }
}
