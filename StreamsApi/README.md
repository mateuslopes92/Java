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

## Functional Interfaces with Streams

This project also demonstrates the most common **functional interfaces** used with Streams.

### Consumer<T>

Consumes a value and returns nothing.

```java
Consumer<Car> printCar = car -> System.out.println(car);
cars.forEach(printCar);
```

* Signature: `T -> void`
* Used for side effects (printing, logging)

---

### Supplier<T>

Produces values without receiving input.

```java
Supplier<Car> carSupplier = () -> new Car("sedan", "Honda", "Civic", 2000);
Car newCar = carSupplier.get();
```

* Signature: `() -> T`
* Commonly used to create objects lazily

---

### UnaryOperator<T>

Transforms a value **into the same type**.

```java
UnaryOperator<Car> upgradeEngine = car ->
    new Car(car.type(), car.make(), car.model(), car.engineCapacity() + 200);
```

* Signature: `T -> T`
* Special case of `Function<T, T>`

---

### BinaryOperator<T>

Combines two values into one.

```java
BinaryOperator<Integer> sumEngines = Integer::sum;

int totalEngineCapacity = cars.stream()
    .map(Car::engineCapacity)
    .reduce(0, sumEngines);
```

* Signature: `(T, T) -> T`
* Often used with `reduce()`

---

## Summary

* `Consumer` performs actions
* `Supplier` creates values
* `UnaryOperator` transforms values
* `BinaryOperator` combines values

Together, these interfaces form the foundation of Java Streams and functional programming.
