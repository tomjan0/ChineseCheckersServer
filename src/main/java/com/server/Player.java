package com.server;


public abstract class Player {
    private String name;
    private int playerId;
    private int gameId;
    private boolean isMoving;
    private int pawnsInPlace;

    public Player(String name){
        this.playerId = Server.playerIdCounter;
        Server.playerIdCounter++;
        this.name = name;
        gameId = -1;
        isMoving = false;
        pawnsInPlace = 0;

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
