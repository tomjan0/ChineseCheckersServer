package com.server;

import java.util.ArrayList;

public class Room {
    private int roomId;
    private ArrayList<Player> players;

    public Room(int roomId, ArrayList<Player> players) {
        this.roomId = roomId;
        this.players = players;
    }

    public Room(int roomId) {
        this(roomId, new ArrayList<>());
    }

    public void addPlayer(Player player) {
        //TODO: implement method
    }


}
