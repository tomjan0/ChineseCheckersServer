package com.server;

import com.sun.source.tree.WhileLoopTree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    private static final int PORT = 2137;
    private static ArrayList<Room> roomList = new ArrayList<>();
    private static ArrayList<Player> playerList = new ArrayList<>();

    public static void main(String[] args) {

        BufferedReader in;
        PrintWriter out;
        System.out.println("Server is running");
        try {
            ServerSocket listener = new ServerSocket(PORT);
            while(true) {
                try {
                    Socket socket = listener.accept();
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    out = new PrintWriter(socket.getOutputStream(), true);
                    System.out.println("Client connected");

                    String input = "";


                    while (!(input.equals("//end"))) {
                        input = in.readLine();
                        System.out.println(input);
                    }
                    out.println("Conection established");

                    for (Room room : roomList) {
                        out.println(room.toString());
                    }
                    out.println("CREATE ROOM?");
                    out.println("//end");

                    input = "";
                    Room room = null;

                    while(!(input.equals("//end"))) {
                        input = in.readLine();
                        if (input.equals("Y")) {
                            input = in.readLine();
                            int number = Integer.parseInt(input);
                            Player player = new HumanPlayer(playerList.size(), socket);
                            room = new Room(roomList.size(), player, number, 1);
                            roomList.add(room);
                        }
                    }
                    out.println("Your room:\n    " + room.toString());
                    out.println("//end");

                    input = "";
                    while (!(input.equals("//end"))) {
                        input = in.readLine();
                        System.out.println(input);
                    }


                    socket.close();
                }
                catch (IOException ex){
                    System.out.println("Connection to client failed");
                }
            }
        } catch (IOException e) {
            System.out.println("Creating socket failed");
        }
    }
}
