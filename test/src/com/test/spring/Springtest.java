package com.test.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Springtest {
    public static void run() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("ApplicationContext.xml");
        userService server = (userService) applicationContext.getBean("userService");
        server.sayHello();
        server.sayHello("msg");
        System.out.println(server.Echo("msg"));
    }
}