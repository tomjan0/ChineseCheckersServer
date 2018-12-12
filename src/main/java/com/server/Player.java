package com.server;


public abstract class Player {
    private int playerId;
    private boolean isPlaying;
    private boolean isMoving;
    private int pawnsInPlace;

    public Player(int id){
        playerId = id;
        isPlaying = false;
        isMoving = false;
        pawnsInPlace = 0;
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
