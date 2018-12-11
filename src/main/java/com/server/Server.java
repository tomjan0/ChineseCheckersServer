package com.server;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {

    private static final int PORT = 2137;

    public static void main(String[] args) {
        System.out.println("Server is running");
        try {
            ServerSocket listener = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
