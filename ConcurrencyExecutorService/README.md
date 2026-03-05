# Java Concurrency (ExecutorService, Callable & Future, Locks and Atomic Variables)

This project contains practical examples of Java Concurrency concepts including:

- ExecutorService
- Thread pools
- Callable and Future
- Lock mechanisms (ReentrantLock, ReentrantReadWriteLock)
- Atomic Variables (CAS / Lock-Free Programming)
- Thread lifecycle management
- Shutdown strategies

The examples are designed for study purposes and demonstrate how Java handles multithreading using the `java.util.concurrent` package.

---

# ExecutorService

ExecutorService is an interface that manages and controls thread execution. Instead of manually creating threads, we delegate task execution to a thread pool.

## Implementations Covered:

### 1. FixedThreadPool (FixedThreadPool.java)

Creates a thread pool with a fixed number of threads.

- Good for CPU-intensive tasks
- Pool size usually equals number of available processors

```java
int coreCount = Runtime.getRuntime().availableProcessors();
ExecutorService service = Executors.newFixedThreadPool(coreCount);
```

### 2. CachedThreadPool (CachedThreadPool.java)

- Creates new threads as needed
- Reuses previously constructed threads
- Good for many short-lived asynchronous tasks
```java
ExecutorService service = Executors.newCachedThreadPool();
```

### 3. SingleThreadExecutor (MySingleThreadExecutor.java)

- Uses only one worker thread
- Guarantees sequential execution
```java
ExecutorService service = Executors.newSingleThreadExecutor();
```

### 4. ScheduledThreadPool (ScheduledThreadPool.java)

Schedules tasks to run after a delay or periodically

```java
ScheduledExecutorService service =
        Executors.newScheduledThreadPool(2);

service.schedule(() -> {
    System.out.println("Executed after delay");
}, 3, TimeUnit.SECONDS);
```

### 5. Task Class (Task.java)

Represents a simple Runnable task used by multiple executor examples.

#### Callable & Future

Unlike Runnable, Callable:
- Returns a result
- Can throw checked exceptions

### 6. Callable Example (CallableExample.java & CallableTask.java)

CallableTask implements Callable<T> and returns a computed result.

```java
Callable<Integer> task = () -> {
    Thread.sleep(1000);
    return 10;
};

Future<Integer> future = executor.submit(task);
Integer result = future.get();
```

### Future
Future represents the result of an asynchronous computation.

Main methods:
- get() → waits for result
- isDone() → checks if finished
- cancel() → attempts cancellation

### 7. Executor Shutdown (ExecutorShutdown.java)

Proper shutdown is critical to avoid resource leaks.

Two main approaches:
```java
executor.shutdown(); // orderly shutdown
```
```java
executor.shutdownNow(); // attempts to stop all running tasks
```

Always ensure executors are properly closed when no longer needed.

### 8. RejectedExecutionHandler (RejectorExecution.java)

Demonstrates what happens when tasks are submitted after executor shutdown or when the queue is full.

Java provides built-in policies:
- AbortPolicy (default)
- CallerRunsPolicy
- DiscardPolicy
- DiscardOldestPolicy

### Locks

The project also demonstrates explicit locking mechanisms.

### 9. Object-Level Lock (ObjectLevelLock.java)

Uses synchronized method.
- Only one thread can execute the method at a time.
- Lock is intrinsic (monitor lock).

```java
public synchronized void display() {
    System.out.println(Thread.currentThread().getName());
}
```

### 10. ReentrantLock (ReentrantLockExample.java)

A more flexible alternative to synchronized.

Features:
- Can attempt lock with tryLock()
- Can use fairness policy
- Must manually unlock
- Supports reentrancy (same thread can lock multiple times)

```java
Lock lock = new ReentrantLock(true);

if(lock.tryLock()) {
    try {
        // critical section
    } finally {
        lock.unlock();
    }
}
```

### 11. ReentrantReadWriteLock (ReentrantReadWriteLockExample.java)

Provides two separate locks:
- Read Lock → multiple readers allowed
- Write Lock → exclusive access

#### Best for read-heavy systems.

```java
ReentrantReadWriteLock rwLock =
        new ReentrantReadWriteLock(true);

Lock readLock = rwLock.readLock();
Lock writeLock = rwLock.writeLock();
```

#### Behavior:
- Multiple threads can read simultaneously
- Only one writer allowed
- Writers block readers
- Readers block writers

### 12. Atomic Variables (AtomicVariables.java)
Demonstrates lock-free thread-safe operations using `AtomicInteger`.

Atomic variables use **CAS (Compare-And-Swap)** internally instead of locks.

#### Key Characteristics
- No `synchronized`
- No `Lock`
- Non-blocking
- High performance under contention
- Thread-safe operations

### Basic Example

```java
private final AtomicInteger counter = new AtomicInteger(0);

public void increment() {
    for (int i = 0; i < 100_000_000; i++) {
        counter.incrementAndGet(); // atomic operation (CAS)
    }
}
```

#### Behavior

- Multiple threads can increment the same counter concurrently
- Each increment is atomic
- No explicit locking required
- Final result remains consistent

Example expected result with two threads:
```aiignore
100,000,000 * 2 = 200,000,000
```


Use Atomic classes when you need simple atomic operations without the overhead of locks.

---

### 13. Main.java

The Main.java class serves as an entry point to test and experiment with the different concurrency implementations.

You can:

- Execute executor examples
- Run Callable/Future logic
- Test locking mechanism
- Test atomic variables
- Observe thread behavior

---

### Summary of Concepts
| Concept                  | Purpose                       |
| ------------------------ | ----------------------------- |
| ExecutorService          | Manages thread pools          |
| FixedThreadPool          | Fixed number of threads       |
| CachedThreadPool         | Dynamic thread creation       |
| SingleThreadExecutor     | Sequential execution          |
| ScheduledExecutorService | Delayed/periodic execution    |
| Callable                 | Returns result from task      |
| Future                   | Holds async result            |
| ReentrantLock            | Explicit mutual exclusion     |
| ReentrantReadWriteLock   | Read-write separation         |
| AtomicInteger            | Lock-free thread-safe counter |
| shutdown()               | Graceful termination          |
| shutdownNow()            | Immediate termination attempt |


## Important Notes

- Always shutdown ExecutorService.
- Always unlock in finally block.
- Prefer thread pools over manual thread creation.
- Use ReadWriteLock when reads >> writes.
- Use Atomic classes when you only need atomic operations.
- Avoid blocking calls inside large thread pools unnecessarily.