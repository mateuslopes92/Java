package com.mateuslopes.SpringWebApp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @RequestMapping("/")
    public String greet(){
        System.out.println("im here");
        return "Welcome to java web!";
    }

    @RequestMapping("/about")
    public String about(){
        return "About page!";
    }

}
