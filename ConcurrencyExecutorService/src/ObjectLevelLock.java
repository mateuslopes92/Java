public class ObjectLevelLock {
    // Using synchronized only one thread can run this method at time
    // the other thread need to wait the lock be released to execute
    public synchronized void display()
    {
        System.out.println(
                Thread.currentThread().getName()
                        + " acquired object-level lock.");
        try {
            Thread.sleep(500);
        }
        catch (InterruptedException e) {
        }
    }

    public static void main(String[] args)
    {
        ObjectLevelLock obj
                = new ObjectLevelLock();

        Thread t1 = new Thread(obj::display, "Thread-1");
        Thread t2 = new Thread(obj::display, "Thread-2");

        t1.start(); // Thread 1 runs the display method
        t2.start(); // Thread 2 wait Thread 1 release the lock to run the display method
    }
}
