package org.example;

import java.util.ArrayList;
import java.util.List;

// Demonstrates how to use Virtual Threads (Project Loom)
// Virtual threads are lightweight threads managed by the JVM
// They are ideal for I/O-bound tasks and allow creating thousands of threads efficiently
public class VirtualThreadExample {

    public static void main(String[] args) {

        System.out.println("-------Virtual Threads-------");

        // List to keep track of threads so we can wait for them to finish
        List<Thread> threads = new ArrayList<>();

        // Create multiple virtual threads
        for (int i = 0; i < 5; i++) {

            int taskId = i;

            // Create and start a virtual thread
            Thread thread = Thread.startVirtualThread(() -> {
                System.out.println("Task " + taskId + " running on " + Thread.currentThread());

                try {
                    // Simulate I/O work
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                System.out.println("Task " + taskId + " finished");
            });

            threads.add(thread);
        }

        // Wait for all threads to finish
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("All tasks completed");
        System.out.println("-----------------------------\n");
    }
}