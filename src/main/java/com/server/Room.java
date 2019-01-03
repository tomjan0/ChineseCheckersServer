package com.server;

import java.util.ArrayList;

public class Room {
    private int roomId;
    private ArrayList<Player> players;
    private int numberOfPlayers;
    private int numberOfAIPlayers;
    private int numberOfConnectedPlayers;

    public Room(int roomId, Player player, int numberOfPlayers, int numberOfAiPlayers ) {
        this.roomId = roomId;
        this.players = new ArrayList<>();
        players.add(player);
        this.numberOfPlayers = numberOfPlayers;
        this.numberOfAIPlayers = numberOfAiPlayers;
        numberOfConnectedPlayers = 1;
    }


    public void addPlayer(Player player) {
        //TODO: implement method
    }

    public String toString(){
        return (roomId + " " + numberOfConnectedPlayers + " " + numberOfPlayers);
    }


}
