package org.example;

import org.example.creational.*;
import org.example.structural.AdapterExample;
import org.example.structural.DecoratorExample;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello and welcome to Design Patterns in Java!");

        // Creational
        SingletonExample.main(args);
        FactoryExample.main(args);
        AbstractFactoryExample.main(args);
        BuilderExample.main(args);
        PrototypeExample.main(args);

        // Structural
        AdapterExample.main(args);
        DecoratorExample.main(args);
    }
}