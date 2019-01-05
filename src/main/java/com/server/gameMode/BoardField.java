package com.server.gameMode;

public class BoardField {
    private PlayerPawn pawn;
    private int winnerId;
    private String outputCode;

    public BoardField() {
        pawn = null;
        outputCode = "o";
    }

    public PlayerPawn getPawn() {
        return pawn;
    }

    public void setPawn(int playerId, int gameId) {
        this.pawn = new PlayerPawn(playerId);
        outputCode = gameId + "";
    }

    public int getWinnerId() {
        return winnerId;
    }

    public void setWinnerId(int winnerId) {
        this.winnerId = winnerId;
    }

    public String getOutputCode() {
        return outputCode;
    }
}
