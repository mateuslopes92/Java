package org.example;

import org.example.behavioral.ObserverExample;
import org.example.behavioral.StrategyExample;
import org.example.creational.*;
import org.example.structural.AdapterExample;
import org.example.structural.DecoratorExample;
import org.example.structural.FacadeExample;
import org.example.structural.ProxyExample;

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
        FacadeExample.main(args);
        ProxyExample.main(args);

        // Behavioral
        StrategyExample.main(args);
        ObserverExample.main(args);
    }
}