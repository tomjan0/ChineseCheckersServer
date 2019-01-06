package com.server;

import com.server.player.Player;

public class RoomThread extends Thread {

    private Room room;
    private int playerId;
    private boolean isStarted = false;

    public boolean joinRoom(int roomId, Player player) {
        room = Server.getRoom(roomId);
        playerId = player.getPlayerId();
        if (room != null && room.addPlayer(player)) {
            return true;
        } else {
            return false;
        }
    }

    public void newPlayerUpdate() {
        for (Player currentPlayer :
                room.getPlayers()) {
            if (playerId != currentPlayer.getPlayerId() && isStarted) {
                sendUpdate("update-room;"
                                + room.getPlayerById(playerId).toString() + " joined the room|" + room.getInfo(currentPlayer)
                        , currentPlayer.getPlayerId());
            }
        }
    }
    @Override
    public synchronized void start() {
        System.out.println("Player #" + playerId + " started game in room #" + room.getRoomId());
        isStarted = true;
        synchronized (this) {
            while (Server.getRoomList().contains(room)) {
                if (room.isFull())  {
                    room.setGameOn(true);
                    for (Player player :
                            room.getPlayers()) {
                        sendUpdate("full-room;false;Let's play", player.getPlayerId());
                    }
                }
                while (room.isGameOn()) {
                    //TODO: implement behaviour during game
                }
            }
            room.closeRoom();
            for (Player player :
                    room.getPlayers()) {
                player.leaveRoom(room.getRoomId());
            }
        }
    }

    //request pattern: "room-request;room-id;[code];[message]"
    public String handleRequest(String request) {
        String requestCode = request.split(";")[2];
        switch (requestCode) {
            case "leave-room": {
                if (room.isGameOn()) {
                    return "error;You cannot leave till game ends";
                } else if (room.getPlayerById(playerId).leaveRoom(room.getRoomId()) && room.removePlayer(playerId)){
                    if (!room.anyHumansLeft()) {
                        room.closeRoom();
                    }
                    return "leave-room;success";
                } else {
                    return "error;You are not in this room";
                }
            }
            case "start-game": {
                start();
                return "start-game;success";
            }
            default: {
                return "error;Operation not allowed";
            }
        }
    }

    public Room getRoom() {
        return room;
    }

    private void sendUpdate(String update, int playerId) {
        ClientHandler client = Server.getClientHandler(playerId);
        if (client != null) {
            client.sendUpdate(update);
        }
    }
}
