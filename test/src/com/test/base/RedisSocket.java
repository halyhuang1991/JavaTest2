package com.test.base;

import java.io.*;
import java.net.Socket;

public class RedisSocket {
    public static void Set() {
        Socket socket;
        try {
            socket = new Socket("127.0.0.1", 6379);
            // oi流
            OutputStream os = socket.getOutputStream();
            InputStream is = socket.getInputStream();

            // 向redis服务器写
            // os.write("auth hello\r\n".getBytes());
            os.write("set hello world123\r\n".getBytes());

            // 从redis服务器读,到bytes中
            byte[] bytes = new byte[1024];
            if (is.read() == '+') {
                System.out.println("这是一个状态回复哦! 怎么知道的呢? `+` 号就表示 '状态回复' 了");
                int len = is.read(bytes);
                System.out.println("回复的状态是: " + new String(bytes, 0, len));
            }
            os.write("get hello\r\n".getBytes());

            // 缓冲数组
            char[] chars = new char[1024];
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            // 从redis服务器读,到bytes中
            if (br.read() == '$') {
                int len = Integer.parseInt(br.readLine());
                br.read(chars, 0, len);
                System.out.println("get到的结果是: " + new String(chars, 0, len) + ", 数一数真的是" + len + "个字符");
            }
            br.close();
            is.close();
            os.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}