package com.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    private static final int PORT = 7554;
    private static ArrayList<Room> roomList = new ArrayList<>();
    private static ArrayList<Player> playerList = new ArrayList<>();

    public static void main(String[] args) {

        System.out.println("Starting server");
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Waiting for the first connection...");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                ServerThread client = new ServerThread(clientSocket);
                Thread game = new Thread(client);
                game.start();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
