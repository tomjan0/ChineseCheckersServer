package com.server.gameMode;

public class BoardField {
    private PlayerPawn pawn;
    private int winnerId;
    private String outputCode;

    BoardField() {
        pawn = null;
        outputCode = "o";
    }

    public PlayerPawn getPawn() {
        return pawn;
    }

    void setPawn(int gameId) {
        this.pawn = new PlayerPawn(gameId);
        outputCode = gameId + "";
    }

    void setPawn(PlayerPawn pawn) {
        this.pawn = pawn;
        outputCode = pawn.getOwnerId() + "";
    }

    int getWinnerId() {
        return winnerId;
    }

    void setWinnerId(int winnerId) {
        this.winnerId = winnerId;
    }

    String getOutputCode() {
        return outputCode;
    }

    void removePawn() {
        this.outputCode = "o";
        pawn = null;
    }
}
