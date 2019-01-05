package com.server;


import java.net.Socket;
import java.util.ArrayList;

public abstract class Player {
    private static int playerIdCounter = 1;

    private String name;
    private int playerId;
    private int gameId;
    private boolean isMoving;
    private int pawnsInPlace;
    private ArrayList<RoomThread> joinedRooms;

    public Player(String name){
        this.playerId = playerIdCounter;
        playerIdCounter++;
        this.name = name;
        gameId = -1;
        isMoving = false;
        pawnsInPlace = 0;
        joinedRooms = new ArrayList<>();
    }

    public boolean joinRoom(int roomId) {
        RoomThread room = new RoomThread();
        boolean result = room.joinRoom(roomId, this);
        if (result) {
            joinedRooms.add(room);
            room.start();
            return true;
        } else {
            return false;
        }
    }

    public static Player getPlayer(String difficultyLevel) {
        return AIPlayer.getAI(difficultyLevel);
    }

    public static Player getPlayer(String name, Socket socket) {
        return new HumanPlayer(name, socket);
    }

    public String getName() {
        return name;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId){
        this.gameId = gameId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public boolean isWinning() {
        return (pawnsInPlace == 10);
    }

    @Override
    public String toString() {
        return name + " #" + playerId;
    }
}
