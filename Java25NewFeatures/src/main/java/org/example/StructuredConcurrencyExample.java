package org.example;

import java.util.concurrent.StructuredTaskScope;

// Allows grouping multiple concurrent tasks and managing them as a single unit
public class StructuredConcurrencyExample {

    public static void main(String[] args) throws Exception {

        System.out.println("-------Structured Concurrency-------");

        // StructuredTaskScope manages multiple tasks together
        // ShutdownOnFailure: if one task fails, all others are stopped
        try (var scope = StructuredTaskScope.open()) {

            var task1 = scope.fork(() -> {
                Thread.sleep(1000);
                return "Result from Task 1";
            });

            var task2 = scope.fork(() -> {
                Thread.sleep(500);
                return "Result from Task 2";
            });

            scope.join();

            System.out.println(task1.get());
            System.out.println(task2.get());
        }

        System.out.println("------------------------------------\n");
    }
}