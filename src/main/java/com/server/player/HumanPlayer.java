package com.server.player;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class HumanPlayer extends Player {
    private PrintWriter out;

    public HumanPlayer(String name, Socket socket) throws IOException {
        super(name);
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    @Override
    public boolean sendMessage(String message) {
        try {
            out.println(message);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
