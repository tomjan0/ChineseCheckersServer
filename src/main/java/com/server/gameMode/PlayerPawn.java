package com.server.gameMode;

public class PlayerPawn {
    private int ownerId;
    private boolean isInPlace;

    PlayerPawn(int ownerId) {
        this.ownerId = ownerId;
        isInPlace = false;
    }

    public int getOwnerId() {
        return ownerId;
    }

    boolean isInPlace() {
        return isInPlace;
    }

    void setInPlace(boolean inPlace) {
        isInPlace = inPlace;
    }
}
