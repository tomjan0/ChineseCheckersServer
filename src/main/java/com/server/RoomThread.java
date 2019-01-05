package com.server;

public class RoomThread extends Thread {

    private Room room;
    private int playerId;

    public boolean joinRoom(int roomId, Player player) {
        room = Server.getRoom(roomId);
        playerId = player.getPlayerId();
        if (room != null) {
            return room.addPlayer(player);
        } else {
            return false;
        }
    }

    @Override
    public synchronized void start() {
        System.out.println("Player #" + playerId + "joined room #" + room.getRoomId());
    }
}
