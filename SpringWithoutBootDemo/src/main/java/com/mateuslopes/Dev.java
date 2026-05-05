package com.mateuslopes;

public class Dev {

    private Laptop laptop;
    private int age;

    public Dev(){
        System.out.println("Dev constructor");
    }

    public Dev(int age) {
        System.out.println("Constructor injection");
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Laptop getLaptop() {
        return laptop;
    }

    public void setLaptop(Laptop laptop) {
        this.laptop = laptop;
    }

    public void build(){
        System.out.println("Building awesome things!");
        laptop.compile();
    }
}
