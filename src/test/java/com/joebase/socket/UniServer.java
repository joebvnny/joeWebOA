package com.joebase.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UniServer {
    
    private static ExecutorService threadPool = Executors.newFixedThreadPool(3);
    
    public static void main(String args[]) throws IOException {
        UniServer tt = new UniServer();
        tt.initServerSocket(7001);
//        tt.initServerChannel(7001);
//        tt.initSelector(7001);
    }

    public void initServerSocket(int port) throws IOException {
        ServerSocket ss = new ServerSocket(port);
        System.out.println(ss);
        while(true) {
            Socket socket = ss.accept();
            Runnable task = wrapTask(socket);
            runTask(task);
        }
    }

    private void runTask(Runnable task) {
        threadPool.execute(task);
    }

    private Runnable wrapTask(final Socket socket) {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                System.out.println(socket + " accepted");
                byte[] buf = new byte[1024];
                try {
                    socket.getInputStream().read(buf);
                } catch(Exception ex) {
                    try {
                        socket.close();
                    } catch(IOException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(Thread.currentThread() + ": " + new String(buf));
            }
        };
        return task;
    }

    public void initServerChannel(int port) throws IOException {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        ServerSocket ss = ssc.socket();
        ss.bind(new InetSocketAddress(port));
        System.out.println(ss);
        while(true) {
            SocketChannel sc = ssc.accept();
            if(sc != null) {
                Socket socket = sc.socket();
                Runnable task = wrapTask(socket);
                runTask(task);
            }
        }
    }

    public void initSelector(int port) throws IOException {
        Selector selector = Selector.open();
        // register server channel
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        ServerSocket ss = ssc.socket();
        ss.bind(new InetSocketAddress(port));
        ssc.register(selector, SelectionKey.OP_ACCEPT);
        while(true) {
            int interestNo = selector.select();
            if(interestNo == 0)
                continue;
            Set<SelectionKey> keys = selector.selectedKeys();
            for(SelectionKey key : keys) {
                // 接受Socket连接请求
                if(key.isAcceptable()) {
                    SocketChannel sc = ssc.accept();
                    try {
                        sc.configureBlocking(false);
                        sc.register(selector, SelectionKey.OP_READ);
                    } catch(Exception ex) {
                        sc.close();
                    }
                    System.out.println("connection accepted");
                    keys.remove(key);
                } else if(key.isReadable()) {
                    SocketChannel sc = (SocketChannel)key.channel();
                    ByteBuffer bbuf = ByteBuffer.allocate(1024);
                    try {
                        sc.read(bbuf);
                    } catch(Exception ex) {
                        sc.close();
                    }
                    System.out.println(new String(bbuf.array()));
                    keys.remove(key);
                } else {
                    keys.remove(key);
                }
                continue;
            }
        }
    }
}
