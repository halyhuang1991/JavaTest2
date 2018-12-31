package com.test.Webserver;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MySocket {
    public void StartServer() {

        try {
            ServerSocket serverSocket = new ServerSocket(10086);
            ExecutorService ThreadPool = Executors.newFixedThreadPool(10);
            // 3、获取输入流，并读取客户端信息
            while (true) {
                Socket socket = serverSocket.accept();

                ThreadPool.execute(new Runnable() {

                    @Override
                    public void run() {
                        InputStream is;
                        try {
                            is = socket.getInputStream();
                            InputStreamReader isr = new InputStreamReader(is);
                            BufferedReader br = new BufferedReader(isr);
                            String info = null;
                            while ((info = br.readLine()) != null) {
                                System.out.println("我是服务器，客户端说：" + info);
                            }
                            OutputStream os = socket.getOutputStream();
                            PrintWriter pw = new PrintWriter(os);
                            pw.write("欢迎您！");
                            pw.flush();
                            pw.close();
                            os.close();
                            br.close();
                            isr.close();
                            is.close();
                            socket.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });

            }

            // 5、关闭资源

            // serverSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void StartCustomer(String msg) {
        // 客户端
        // 1、创建客户端Socket，指定服务器地址和端口
        Socket socket;
        try {
            socket = new Socket("localhost", 10086);
            // 2、获取输出流，向服务器端发送信息
            OutputStream os = socket.getOutputStream();// 字节输出流
            PrintWriter pw = new PrintWriter(os);// 将输出流包装成打印流
            pw.write(msg);
            pw.flush();
            socket.shutdownOutput();
            // 3、获取输入流，并读取服务器端的响应信息
            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String info = null;
            while ((info = br.readLine()) != null) {
                System.out.println("我是客户端，服务器说：" + info);
            }

            // 4、关闭资源
            br.close();
            is.close();
            pw.close();
            os.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void run() {
        ThreadDemo01 threadDemo01 = new ThreadDemo01();
        threadDemo01.setName("我是服务端的线程1");
        threadDemo01.start();
        for (Integer i = 0; i < 4; i++) {
            ThreadDemo02 threadDemo02 = new ThreadDemo02();
            threadDemo02.setName("我是客户端的线程" + i);
            threadDemo02.start();
        }

    }
}