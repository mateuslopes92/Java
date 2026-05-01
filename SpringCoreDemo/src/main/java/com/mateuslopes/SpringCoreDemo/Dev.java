package com.mateuslopes.SpringCoreDemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class Dev {

    @Autowired // Field injection (Connects the 2 components each other)
    @Qualifier("laptop") // in case of confusion on 2 classes with @Component we can choose here
    private Computer computer;

//    via Constructor(default) works as well as option instead of Autowired
//    public Dev(Computer laptop){
//        this.computer = computer;
//    }

//    @Autowired // Setter Injection
//    public void setLaptop(Computer computer){
//        this.computer = computer;
//    }

    public void build(){

        computer.compile();

        System.out.println("Building awesome things!");
    }
}
