package com.joebase.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class UniClient {

    public static void main(String args[]) throws UnknownHostException, IOException {
        Socket sc = new Socket("127.0.0.1", 7777);
//        Socket sc = new Socket("119.4.240.104", 7001);
        System.out.println(sc);
        OutputStream out = sc.getOutputStream();
        out.write(("StrongIT IBMS Socket test..." + System.currentTimeMillis()).getBytes());
        out.flush();
        while(true) {
            InputStream in = sc.getInputStream();
            byte[] buffer = new byte[1024];
            while(in.read(buffer) != -1) {
                System.out.println(new String(buffer));
            }
        }
    }
}