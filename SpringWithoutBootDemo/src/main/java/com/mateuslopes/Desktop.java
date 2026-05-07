package com.mateuslopes;

public class Desktop implements Computer {

    public Desktop(){
        System.out.println("Desktop constructor");
    }

    public void compile(){
        System.out.println("Compiling in Desktop!");
    }
}
