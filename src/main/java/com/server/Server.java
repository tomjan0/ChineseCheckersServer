package com.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    private static final int PORT = 7554;
    private static  ArrayList<Room> roomList = new ArrayList<>();
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
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static ArrayList<Room> getRoomList() {
        return roomList;
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

    public static int createRoom(String roomData, ClientHandler client){
        String[] data = roomData.split(";");
        String gameMode = data[1];
        int playersNo = Integer.parseInt(data[2]);
        int aiNo = Integer.parseInt(data[3]);
        Room room = new Room();
        roomList.add(room);
        room.customizeRoom(client.getPlayer(), playersNo, aiNo, gameMode);
        return room.getRoomId();
    }

    public static void closeRoom(int roomId) {
        getRoom(roomId).getPlayers().removeAll(getRoom(roomId).getPlayers());
        roomList.remove(getRoom(roomId));
        System.out.println("Room #" + roomId + " closed");
    }

    public static String getCurrentRoomList(){
        StringBuilder currentList = new StringBuilder();
        for (Room room : Server.roomList) {
            currentList.append(room.toString());
            currentList.append(";");
        }
        return currentList.toString();
    }

    public static String getListOfGameModes(){
        return Room.getListOfGameModes();
    }

    public static String getRoomCapacityList(String gameMode) {
        return Room.getCapacityList(gameMode);
    }

}
