package com.server;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

public class HumanPlayer extends Player {
    private Socket playerSocket;
    private BufferedReader in;
    private PrintWriter out;

    public HumanPlayer() {
    }

    public void sendMessage(String message) {
        out.print(message);
    }

    public String receiveMessage(){
        return "";
    }



}
