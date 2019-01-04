package com.server;

//import test.TestGUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThread implements Runnable {

//    private int id;
    private Player player;
    private PrintWriter out;
    private BufferedReader in;
    private Socket clientSocket;

    public ServerThread(Socket socket, int id) {
        try {
            clientSocket = socket;
            System.out.println(clientSocket.getInetAddress().toString() + " Connected");
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//            this.id = id;
        } catch (IOException e) {
            closeSocket();
        }
    }

    @Override
    public void run() {
        try {
            String message = in.readLine();
            do {
                System.out.println("\t" + clientSocket.getInetAddress() +" Client request: " + message);
                chooseResponse(message);
                message = in.readLine();
            } while (message != null);
        } catch (IOException ex) {
//                System.out.println("Exception");
        }
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    private void chooseResponse(String message) {
        if (message.equals("close")) {
            closeSocket();
        } else {
            //TODO: call method that runs appropriate code depending on message
            out.println(Rules.respond(message, this));
//            if (message.equals("port-request")) {
//                out.println("6363");
//            }
//            if (message.equals("Player")) {
//                out.println("492");
//            }
//            if (message.equals("new-room")){
//                out.println("1;4;3;Basic;2;3;4;6;1;2;3");
//            }
        }
    }

    public void closeSocket() {
        try {
            clientSocket.close();
            System.out.println(clientSocket.getInetAddress().toString() + " Disconnected");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
