package com.server;

import java.net.Socket;

public class HumanPlayer extends Player {
    private Socket socket;

    public HumanPlayer(String name, Socket socket){
        super(name);
        this.socket = socket;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
