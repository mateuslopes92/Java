Java Concurrency (ExecutorService, Callable & Future, Locks)

This project contains practical examples of Java Concurrency concepts including:

ExecutorService

Thread pools

Callable and Future

Lock mechanisms (ReentrantLock, ReentrantReadWriteLock)

Thread lifecycle management

Shutdown strategies

The examples are designed for study purposes and demonstrate how Java handles multithreading using the java.util.concurrent package.

ExecutorService

ExecutorService is an interface that manages and controls thread execution. Instead of manually creating threads, we delegate task execution to a thread pool.

Implementations Covered:
1️⃣ FixedThreadPool (FixedThreadPool.java)

Creates a thread pool with a fixed number of threads.

Good for CPU-intensive tasks

Pool size usually equals number of available processors

int coreCount = Runtime.getRuntime().availableProcessors();
ExecutorService service = Executors.newFixedThreadPool(coreCount);
2️⃣ CachedThreadPool (CachedThreadPool.java)

Creates new threads as needed

Reuses previously constructed threads

Good for many short-lived asynchronous tasks

ExecutorService service = Executors.newCachedThreadPool();
3️⃣ SingleThreadExecutor (MySingleThreadExecutor.java)

Uses only one worker thread

Guarantees sequential execution

ExecutorService service = Executors.newSingleThreadExecutor();
4️⃣ ScheduledThreadPool (ScheduledThreadPool.java)

Schedules tasks to run after a delay or periodically

ScheduledExecutorService service =
        Executors.newScheduledThreadPool(2);

service.schedule(() -> {
    System.out.println("Executed after delay");
}, 3, TimeUnit.SECONDS);
5️⃣ Task Class (Task.java)

Represents a simple Runnable task used by multiple executor examples.

Callable & Future

Unlike Runnable, Callable:

Returns a result

Can throw checked exceptions

Callable Example (CallableExample.java & CallableTask.java)

CallableTask implements Callable<T> and returns a computed result.

Callable<Integer> task = () -> {
    Thread.sleep(1000);
    return 10;
};

Future<Integer> future = executor.submit(task);
Integer result = future.get();
Future

Future represents the result of an asynchronous computation.

Main methods:

get() → waits for result

isDone() → checks if finished

cancel() → attempts cancellation

Executor Shutdown (ExecutorShutdown.java)

Proper shutdown is critical to avoid resource leaks.

Two main approaches:

executor.shutdown(); // orderly shutdown
executor.shutdownNow(); // attempts to stop all running tasks

Always ensure executors are properly closed when no longer needed.

RejectedExecutionHandler (RejectorExecution.java)

Demonstrates what happens when tasks are submitted after executor shutdown or when the queue is full.

Java provides built-in policies:

AbortPolicy (default)

CallerRunsPolicy

DiscardPolicy

DiscardOldestPolicy

Locks

The project also demonstrates explicit locking mechanisms.

Object-Level Lock (ObjectLevelLock.java)

Uses synchronized method.

Only one thread can execute the method at a time.

Lock is intrinsic (monitor lock).

public synchronized void display() {
    System.out.println(Thread.currentThread().getName());
}
ReentrantLock (ReentrantLockExample.java)

A more flexible alternative to synchronized.

Features:

Can attempt lock with tryLock()

Can use fairness policy

Must manually unlock

Supports reentrancy (same thread can lock multiple times)

Lock lock = new ReentrantLock(true);

if(lock.tryLock()) {
    try {
        // critical section
    } finally {
        lock.unlock();
    }
}
ReentrantReadWriteLock (ReentrantReadWriteLockExample.java)

Provides two separate locks:

Read Lock → multiple readers allowed

Write Lock → exclusive access

Best for read-heavy systems.

ReentrantReadWriteLock rwLock =
        new ReentrantReadWriteLock(true);

Lock readLock = rwLock.readLock();
Lock writeLock = rwLock.writeLock();
Behavior:

Multiple threads can read simultaneously

Only one writer allowed

Writers block readers

Readers block writers

Main.java

The Main.java class serves as an entry point to test and experiment with the different concurrency implementations.

You can:

Execute executor examples

Run Callable/Future logic

Test locking mechanisms

Observe thread behavior

Summary of Concepts
Concept	Purpose
ExecutorService	Manages thread pools
FixedThreadPool	Fixed number of threads
CachedThreadPool	Dynamic thread creation
SingleThreadExecutor	Sequential execution
ScheduledExecutorService	Delayed/periodic execution
Callable	Returns result from task
Future	Holds async result
ReentrantLock	Explicit mutual exclusion
ReentrantReadWriteLock	Read-write separation
shutdown()	Graceful termination
shutdownNow()	Immediate termination attempt
Important Notes

Always shutdown ExecutorService.

Always unlock in finally block.

Prefer thread pools over manual thread creation.

Use ReadWriteLock when reads >> writes.

Avoid blocking calls inside large thread pools unnecessarily.