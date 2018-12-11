package com.test.spring;

public class SpringHelloWorld implements userService {
    public void sayHello() {
        System.out.println("Spring say Hello!");
    }

    public void sayHello(String msg) {
        System.out.println(msg);
    }

    public String Echo(String msg) {
        return msg + "--echo";
    }
}