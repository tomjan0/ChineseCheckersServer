package com.server;

import com.server.gameMode.Board;
import com.server.gameMode.Rules;
import com.server.player.Player;

import java.util.ArrayList;

public class Room {
    private static int roomCounter = 1;
    private int roomId;
    private ArrayList<Player> players;
    private int numberOfPlayers;
    private int numberOfAIPlayers;
    private String gameMode;
    private Rules rules;
    private Board board;

    public Room(Player player, int numberOfPlayers, int numberOfAiPlayers, String gameMode) {
        this.roomId = roomCounter;
        roomCounter++;
        this.players = new ArrayList<>();
        this.numberOfPlayers = numberOfPlayers;
        this.numberOfAIPlayers = numberOfAiPlayers;
        this.gameMode = gameMode;
        this.rules = Rules.getRuleset(gameMode);
        this.board = Board.getBoardType(gameMode);
        Server.getRoomList().add(this);
        player.joinRoom(roomId);
        for (int i = 0; i < this.numberOfAIPlayers; i++) {
            Player.getPlayer("Easy").joinRoom(roomId);
        }
    }

    public boolean addPlayer(Player player) {
        if (rules != null && board != null && players.size() < numberOfPlayers && !isAlreadyPlaying(player)) {
            player.setGameId(rules.setPlayerId(numberOfPlayers, players.size()));
            players.add(player);
            board.addPlayer(player.getPlayerId(), player.getGameId());
            System.out.println("Room #" + roomId + " has " + players.size() + " players now");
            return true;
        } else {
            return false;
        }
    }

    public String toString(){
        return (roomId + " " + players.size() + " " + numberOfPlayers);
    }

    public int getRoomId() {
        return roomId;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public String getGameMode() {
        return gameMode;
    }

    public String getListOfPlayers() {
        StringBuilder list = new StringBuilder(players.size() + "");
        for (Player player :
                players) {
            list.append(";" + player.toString());
        }
        return list.toString();
    }

    public String getInfo(Player player) {
        return roomId + ";"
                + "Created by " + players.get(0).toString() + ";"
                + getListOfPlayers() + ";"
                + board.getListOfColors()
                + gameMode + ";"
                + player.getGameId() + ";"
                + board.toString();
    }

    private boolean isAlreadyPlaying(Player player) {
        return players.contains(player);
    }

    public static String getListOfGameModes() {
        return Rules.listOfGameModes;
    }

    public static String getCapacityList(String gameMode) {
        Rules rules = Rules.getRuleset(gameMode);
        if (rules != null) {
            return rules.getCapacityList();
        } else {
            return null;
        }
    }
}
