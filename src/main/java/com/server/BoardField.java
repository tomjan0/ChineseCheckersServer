package com.server;

public class BoardField {
    private PlayerPawn pawn;
    private Player winner;

    public BoardField(PlayerPawn pawn, Player winner) {
        this.pawn = pawn;
        this.winner = winner;
    }

    public BoardField() {
        pawn = null;
        winner = null;
    }

    public void setField(PlayerPawn pawn, Player winner){
        this.pawn = pawn;
        this.winner = winner;
    }

    public Player getPlayer() {
        if (pawn != null) {
            return pawn.getOwner();
        } else {
            return null;
        }
    }
}
