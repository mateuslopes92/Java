package org.example;

import org.example.creational.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello and welcome to Design Patterns in Java!");

        SingletonExample.main(args);
        FactoryExample.main(args);
        AbstractFactoryExample.main(args);
        BuilderExample.main(args);
        PrototypeExample.main(args);
    }
}