package com.server;

public class GameThread extends Thread {

    private ClientHandler owner;

    public GameThread(ClientHandler owner) {
        this.owner = owner;
    }

}
