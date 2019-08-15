package com.test.base;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.*;

public class SocketNIO {
    public final static int PORT = 17531;
    private static ByteBuffer bb = ByteBuffer.allocate(1024);
    private static ServerSocketChannel ssc;
    private static boolean closed = false;

    /**
     * 发送测试数据包，若失败则认为该socket失效
     *
     * @param sk SelectionKey
     * @throws IOException IOException
     */
    private static void writeDataToSocket(SelectionKey sk) throws IOException {
        SocketChannel sc = (SocketChannel) sk.channel();
        bb.clear();
        String str = "server data";
        bb.put(str.getBytes());
        while (bb.hasRemaining()) {
            sc.write(bb);
        }
    }

    /**
     * 从通道中读取数据
     *
     * @param sk SelectionKey
     * @throws IOException IOException
     */
    private static void readDataFromSocket(SelectionKey sk) throws IOException {
        SocketChannel sc = (SocketChannel) sk.channel();
        bb.clear();
        List<Byte> list = new ArrayList<>();
        while (sc.read(bb) > 0) {
            bb.flip();
            while (bb.hasRemaining()) {
                list.add(bb.get());
            }
            bb.clear();
        }
        byte[] bytes = new byte[list.size()];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = list.get(i);
        }
        String s = (new String(bytes)).trim();
        if (!s.isEmpty()) {
            if ("exit".equals(s)) {
                ssc.close();
                closed = true;
            }
            System.out.println("服务器收到：" + s);
        }
    }

    public static void serverRun() throws IOException {
        ssc = ServerSocketChannel.open();// 新建NIO通道
        ssc.configureBlocking(false);// 使通道为非阻塞
        ServerSocket ss = ssc.socket();
        ss.bind(new InetSocketAddress("127.0.0.1", 8189));
        Selector selector = Selector.open();
        SelectionKey skey = ssc.register(selector, SelectionKey.OP_ACCEPT);
        while (true) {
            // 这里会发生阻塞，等待就绪的通道,但在每次select()方法调用之间，只有一个通道就绪了。
            int n = selector.select();
            // 没有就绪的通道则什么也不做
            if (n == 0) {
                continue;
            }
            // 获取SelectionKeys上已经就绪的集合
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

            // 遍历每一个Key
            while (iterator.hasNext()) {
                SelectionKey sk = iterator.next();
                // 通道上是否有可接受的连接
                if (sk.isAcceptable()) {
                    ServerSocketChannel sscTmp = (ServerSocketChannel) sk.channel();
                    SocketChannel sc = sscTmp.accept(); // accept()方法会一直阻塞到有新连接到达。
                    sc.configureBlocking(false);
                    sc.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                } else if (sk.isReadable()) { // 通道上是否有数据可读
                    try {
                        readDataFromSocket(sk);
                    } catch (IOException e) {
                        sk.cancel();
                        continue;
                    }
                }
                if (sk.isWritable()) { // 测试写入数据，若写入失败在会自动取消注册该键
                    try {
                        writeDataToSocket(sk);
                    } catch (IOException e) {
                        sk.cancel();
                        continue;
                    }
                }
                // 必须在处理完通道时自己移除。下次该通道变成就绪时，Selector会再次将其放入已选择键集中。
                iterator.remove();
            } // . end of while

        }
    }
}