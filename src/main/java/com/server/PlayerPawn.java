package com.server;

public class PlayerPawn {
    private int ownerId;
    private boolean isInPlace;

    public PlayerPawn(int ownerId) {
        this.ownerId = ownerId;
        isInPlace = false;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public boolean isInPlace() {
        return isInPlace;
    }

    public void setInPlace(boolean inPlace) {
        isInPlace = inPlace;
    }
}
