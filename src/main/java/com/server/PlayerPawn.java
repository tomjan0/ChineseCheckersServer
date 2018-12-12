package com.server;

public class PlayerPawn {
    private Player owner;
    private boolean isInPlace;

    public PlayerPawn(Player owner) {
        this.owner = owner;
        isInPlace = false;
    }

    public Player getOwner() {
        return owner;
    }
}
