package com.server;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

public class HumanPlayer extends Player {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public HumanPlayer(int id, Socket socket){
        super(id);
        this.socket = socket;
    }

    public void sendMessage(String message) {
        out.print(message);
    }

    public String receiveMessage(){
        return "";
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
