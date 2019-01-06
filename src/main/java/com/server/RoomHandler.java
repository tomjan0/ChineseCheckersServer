package com.server;

import com.server.player.Player;

public class RoomHandler {

    private Room room;
    private int playerId;
//    public boolean isStarted = false;

    public boolean joinRoom(int roomId, Player player) {
        room = Server.getRoom(roomId);
        playerId = player.getPlayerId();
        if (room != null && room.addPlayer(player)) {
            return true;
        } else {
            return false;
        }
    }

//    public void newPlayerUpdate() {
//        for (Player currentPlayer :
//                room.getPlayers()) {
//            if (playerId != currentPlayer.getPlayerId() && isStarted ) {
//                sendUpdate("update-room;"
//                                + room.getPlayerById(playerId).toString() + " joined the room|"
//                                + room.getInfo(currentPlayer)
//                        , currentPlayer.getPlayerId());
//            }
//        }
//    }

    //request pattern: "room-request;room-id;[code];[message]"
    public String handleRequest(String request) {
        String requestCode = request.split(";")[2];
        switch (requestCode) {
            case "leave-room": {
                if (room.isGameOn()) {
                    return "error;You cannot leave till game ends";
                } else if (room.getPlayerById(playerId).leaveRoom(room.getRoomId()) && room.removePlayer(playerId)){
                    if (!room.anyHumansLeft()) {
                        Server.closeRoom(room.getRoomId());
                    }
                    return "leave-room;success";
                } else {
                    return "error;You are not in this room";
                }
            }
            case "start-game": {
                room.startGame();
                return "null";
            }
            case "game-on": {
                return room.getRules().handleRequest(request.split("<")[1]);
            }
            case "end-turn": {
                room.endTurn();
                return "null";
            }
            default: {
                return "error;Operation not allowed";
            }
        }
    }

    public Room getRoom() {
        return room;
    }

//    private boolean sendUpdate(String update, int playerId) {
//        return room.getPlayerById(playerId).sendMessage(update);
//    }
}
