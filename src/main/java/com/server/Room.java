package com.server;

import com.server.gameMode.Board;
import com.server.gameMode.Rules;
import com.server.player.HumanPlayer;
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
    private boolean isGameOn;

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
        isGameOn = false;
    }

    public boolean addPlayer(Player player) {
        if (rules != null && board != null && !isFull() && !isAlreadyPlaying(player)) {
            player.setGameId(rules.setPlayerId(numberOfPlayers, players.size()));
            players.add(player);
            board.addPlayer(player.getPlayerId(), player.getGameId());
            System.out.println("Room #" + roomId + " has " + players.size() + " players now");
            return true;
        } else {
            return false;
        }
    }

    public boolean removePlayer(int playerId) {
        Player player = getPlayerById(playerId);
        if (player != null) {
            players.remove(player);
            board.removePlayer(player.getGameId());
            player.setGameId(-1);
            return true;
        } else {
            return false;
        }

    }

    public boolean anyHumansLeft() {
        int humans = 0;
        for (Player player :
                players) {
            if (player instanceof HumanPlayer) {
                humans++;
            }
        }
        return humans > 0;
    }

    public void closeRoom() {
        players = null;
        Server.getRoomList().remove(this);
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

    public String getMoves(int y, int x){
        return rules.showPossibleMoves(y,x,board);
    }

    public void movePawn(String[] data){
        int y1 = Integer.parseInt(data[1]);
        int x1 = Integer.parseInt(data[2]);
        int y2 = Integer.parseInt(data[3]);
        int x2 = Integer.parseInt(data[4]);
        board.movePawn(y1,x1,y2,x2);
    }

    public boolean isGameOn() {
        return isGameOn;
    }

    public void setGameOn(boolean gameOn) {
        if (gameOn) {
            System.out.println("Game in room #" + getRoomId() + " has started");
        } else {
            System.out.println("Game in room #" + getRoomId() + " has finished");
        }
        isGameOn = gameOn;
    }

    public Player getPlayerById(int playerId) {
        for (Player player :
                players) {
            if (player.getPlayerId() == playerId) {
                return player;
            }
        }
        return null;
    }

    public boolean isFull() {
        return players.size() == numberOfPlayers;
    }

    public String getBoard() {
        return board.toString();
    }
}
