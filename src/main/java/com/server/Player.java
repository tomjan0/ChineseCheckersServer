package com.server;


public abstract class Player {
    private int playerId;
    private int gameId;
    private boolean isMoving;
    private int pawnsInPlace;

    public int getGameId() {
        return gameId;
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
