package com.server;

public class BoardField {
    private PlayerPawn pawn;
    private Player winner;

    public BoardField() {
        pawn = null;
        winner = null;
    }

    public Player getPlayer() {
        if (pawn != null) {
            return pawn.getOwner();
        } else {
            return null;
        }
    }

    public void setPlayer(Player player){
        pawn = new PlayerPawn(player);
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public Player getWinner() {
        return winner;
    }
}
