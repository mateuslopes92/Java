# Java Stream API – Study Notes

This repository contains **simple examples** of the Java Stream API, focused on clarity and readability for **beginner to intermediate** developers.

Streams are used here to process a list of cars using common operations like `filter`, `map`, `flatMap`, and collectors.

---

## What is a Stream?

A **Stream** is a way to process data from a collection (like a `List`) using a **pipeline**:

```
SOURCE → OPERATIONS → RESULT
```

Important points:

* A Stream does **not store data**
* It processes data from a collection
* Nothing happens until a **terminal operation** is called

---

## Record Used in This Project

```java
record Car(String type, String make, String model, Integer engineCapacity) {}
```

A `record` is a simple data holder:

* Immutable by default
* Automatically provides constructor, getters, and `toString()`
* Ideal for modeling data

---

## Stream Operations Covered

### filter()

Used to **keep only elements that match a condition**.

```java
Predicate<Car> isSedan = car -> car.type().equals("sedan");
```

* Receives a `Predicate<T>` (`T -> boolean`)
* `true` → element stays
* `false` → element is removed

---

### map()

Used to **transform elements**.

```java
cars.stream().map(Car::make)
```

* One input → one output
* Does not change the number of elements

---

### flatMap()

Used when **one element produces multiple values**.

```java
cars.stream().flatMap(car -> Stream.of(car.make(), car.model()))
```

* Each element is converted into a Stream
* All Streams are flattened into one

---

## Collectors

### partitioningBy()

Splits data into **true / false groups**.

```java
Map<Boolean, List<Car>> partitionedCars = 
    cars.stream().collect(Collectors.partitioningBy(isSedan));
```

---

### groupingBy()

Groups elements by a key.

```java
Map<String, Map<String, Integer>> groupedCars = cars.stream()
    .collect(Collectors.groupingBy(
        Car::type,
        Collectors.toMap(Car::make, Car::engineCapacity)
    ));
```

---

## Parallel Streams

Parallel streams allow operations to run on **multiple threads**.

```java
cars.parallelStream().forEach(System.out::println);
```

Notes:

* Disabled by default
* Can improve performance for large datasets
* Order is **not guaranteed**

---

## Summary

* Streams process data, they don’t store it
* `filter` removes elements
* `map` transforms elements
* `flatMap` expands elements
* Collectors transform streams into Maps or Lists
* Parallel streams use multiple threads

This project is meant to be a **hands-on reference** for learning Java Streams step by step.
