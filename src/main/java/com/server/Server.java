package com.server;

import com.sun.source.tree.WhileLoopTree;
import test.TestGUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    private static int port = 1025;
    private static ArrayList<Room> roomList = new ArrayList<>();
    private static ArrayList<Player> playerList = new ArrayList<>();

    public static void main(String[] args) {

        System.out.println("Starting server");


        System.out.println("Entering listening loop");
        while (true) {
            try {

                ServerSocket serverSocket = new ServerSocket(1024);

                System.out.println("Waiting for connection...");

                Socket clientSocket = serverSocket.accept();

                System.out.println(clientSocket.getInetAddress().toString() + " Connected");

                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                String message = "";

                while ((message = in.readLine()) != null) {
                    System.out.println(message);
                    if(message.equals("port-request")) {
                        out.println("p" + port);
                        port++;
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
}
