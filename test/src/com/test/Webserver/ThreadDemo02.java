package com.test.Webserver;

public class ThreadDemo02 extends Thread {
    public void run() {
        // 编写自己的线程代码
        System.out.println(Thread.currentThread().getName());
        MySocket my = new MySocket();
        my.StartCustomer(Thread.currentThread().getName());
    }
}