import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.Lock;

// Good for many reads | few writes
// 2 Locks (Read + Write)
// Multiple readers or one writer
public class ReentrantReadWriteLockExample {

    // true = fair mode (maintains acquisition order)
    private static final ReentrantReadWriteLock rwLock =
            new ReentrantReadWriteLock(true);

    private static final Lock readLock = rwLock.readLock();
    private static final Lock writeLock = rwLock.writeLock();

    private static int counter = 0;

    public static void main(String[] args) {

        Runnable readTask = () -> {
            if (readLock.tryLock()) {
                try {
                    System.out.println(Thread.currentThread().getName() + " acquired READ lock");
                    Thread.sleep(2000);
                    System.out.println(Thread.currentThread().getName() + " read value: " + counter);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    System.out.println(Thread.currentThread().getName() + " released READ lock");
                    readLock.unlock();
                }
            } else {
                System.out.println(Thread.currentThread().getName() + " could NOT acquire READ lock");
            }
        };

        Runnable writeTask = () -> {
            if (writeLock.tryLock()) {
                try {
                    System.out.println(Thread.currentThread().getName() + " acquired WRITE lock");
                    Thread.sleep(2000);
                    counter++;
                    System.out.println(Thread.currentThread().getName() + " updated value to: " + counter);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    System.out.println(Thread.currentThread().getName() + " released WRITE lock");
                    writeLock.unlock();
                }
            } else {
                System.out.println(Thread.currentThread().getName() + " could NOT acquire WRITE lock");
            }
        };

        new Thread(readTask, "Reader-1").start();
        new Thread(readTask, "Reader-2").start();
        new Thread(writeTask, "Writer-1").start();
    }
}