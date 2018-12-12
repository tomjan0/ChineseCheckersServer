package com.server;


public abstract class Player {
    private int playerId;
    private int gameId;
    private boolean isMoving;
    private int pawnsInPlace;

    public Player(int playerId){
        this.playerId = playerId;
        gameId = -1;
        isMoving = false;
        pawnsInPlace = 0;

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
}
