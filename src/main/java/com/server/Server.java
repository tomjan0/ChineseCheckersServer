package com.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    private static final int PORT = 7554;
    private static  ArrayList<Room> roomList = new ArrayList<>();
    private static ArrayList<ClientHandler> connectedList = new ArrayList<>();
    public static int roomCounter = 1;
    public static void main(String[] args) {

        System.out.println("Starting server");
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Waiting for the first connection...");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                ClientHandler client = new ClientHandler(clientSocket);
                Thread connection = new Thread(client);
                connection.start();
                connectedList.add(client);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static ArrayList<Room> getRoomList() {
        return roomList;
    }

    public static ArrayList<ClientHandler> getConnectedList() {
        return connectedList;
    }

    public static Room getRoom(int roomId) {
        for (Room room :
                roomList) {
            if (room.getRoomId() == roomId) {
                return room;
            }
            }
        return null;
    }
}
