package com.server;

import test.TestGUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread implements Runnable {
    Socket clientSocket;
    PrintWriter out;
    BufferedReader in;

    public ServerThread(int port) {

        try {
            ServerSocket serverSocket = new ServerSocket(port);

            System.out.println("Waiting for connection...");

            clientSocket = serverSocket.accept();

            System.out.println(clientSocket.getInetAddress().toString() + " Connected");

            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        } catch (IOException ex) {
//                System.out.println("Exception");
        }
    }

    @Override
    public void run() {
        try {
            String message = "";

            while ((message = in.readLine()) != null) {
                System.out.println(message);
                if(message.equals("port-request")) {

                }
                if(message.equals("pend")){
                    out.println("disconnected");
                    break;
                }
            }

            clientSocket.close();

        } catch (IOException ex) {
//                System.out.println("Exception");
        }

    }
}
