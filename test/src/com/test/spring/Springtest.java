package com.test.spring;

import com.test.spring.Aop.Calculator;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Springtest {
    public static void run() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("ApplicationContext.xml");
        userService server = (userService) applicationContext.getBean("userService");
        // server.sayHello();
        // server.sayHello("msg");
        // System.out.println(server.Echo("msg"));
        Calculator arithmeticCalculator = applicationContext.getBean(Calculator.class);
        double result = arithmeticCalculator.div(2, 2);
        System.out.println(result);
    }
}