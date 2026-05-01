package com.mateuslopes.SpringCoreDemo;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
//@Primary // in case of confusion on 2 classes with @Component we can choose here the primary
public class Laptop implements Computer {

    public void compile(){
        System.out.println("Compile with 404 bugs");
    }

}
