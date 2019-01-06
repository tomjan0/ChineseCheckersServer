package com.server.player;


import com.server.RoomHandler;
import com.server.Server;
import com.server.gameMode.Rules;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public abstract class Player {
    private static int playerIdCounter = 1;

    private String name;
    private int playerId;
    private int gameId;
    private ArrayList<RoomHandler> joinedRooms;

    public abstract boolean sendMessage(String message);

    Player(String name){
        this.playerId = playerIdCounter;
        playerIdCounter++;
        this.name = name;
        gameId = -1;
        joinedRooms = new ArrayList<>();
    }

    public boolean joinRoom(int roomId) {
        RoomHandler room = new RoomHandler();
        if (room.joinRoom(roomId, this)) {
            joinedRooms.add(room);
//            if (this instanceof HumanPlayer) room.newPlayerUpdate();
            System.out.println("Player #" + playerId + " joined game room " + room.getRoom().getRoomId() + " on seat " + gameId);
            return true;
        } else {
            return false;
        }
    }

    public boolean leaveRoom(int roomId) {
        if(!getRoomThreadById(roomId).getRoom().anyHumansLeft()){
//        System.out.println("halko");
            Server.closeRoom(getRoomThreadById(roomId).getRoom().getRoomId());
        }
        return joinedRooms.remove(getRoomThreadById(roomId));
    }

    public void leave() {
        ArrayList<Integer> toRemove = new ArrayList<>();
        for (RoomHandler joinedRoom :
                joinedRooms) {
            if (joinedRoom.getRoom().isGameOn()) {
                if(joinedRoom.getRoom().getRules().whoseTurn() == gameId) {
                    joinedRoom.getRoom().getPlayers().remove(this);
                    joinedRoom.getRoom().endTurn();
                }
                setGameId(-1);
            }
            toRemove.add(joinedRoom.getRoom().getRoomId());
            joinedRoom.getRoom().removePlayer(playerId);

        }
        for (int id :
                toRemove) {
            leaveRoom(id);
        }
    }
    public static Player getPlayer(String difficultyLevel, Rules rules) {
        return AIPlayer.getAI(difficultyLevel, rules);
    }

    public static Player getPlayer(String name, Socket socket) throws IOException {
        return new HumanPlayer(name, socket);
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId){
        this.gameId = gameId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public RoomHandler getRoomThreadById(int roomId) {
        for (RoomHandler room : joinedRooms) {
            if (room.getRoom().getRoomId() == roomId) {
                return room;
            }
        }
        return null;
    }

    public boolean isHuman() {
        return this instanceof HumanPlayer;
    }

    @Override
    public String toString() {
        return name + " #" + playerId;
    }
}
