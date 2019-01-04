package com.server;

import java.util.ArrayList;

public class Room {
    private int roomId;
    private ArrayList<Player> players;
    private ArrayList<GameThread> humanPlayers;
    private int numberOfPlayers;
    private int numberOfAIPlayers;
    private int numberOfConnectedPlayers;
    private String gameMode;
    private Rules rules;
    private Board board;

    public Room(Player player, int numberOfPlayers, int numberOfAiPlayers, String gameMode) {
        this.roomId = Server.roomCounter;
        Server.roomCounter++;
        this.players = new ArrayList<>();
        this.numberOfPlayers = numberOfPlayers;
        this.numberOfAIPlayers = numberOfAiPlayers;
        humanPlayers = new ArrayList<>();
        numberOfConnectedPlayers = 1;
        this.gameMode = gameMode;
        this.rules = Rules.getRuleset(gameMode);
        switch (gameMode) {
            case "Basic": {
                board = new BasicBoard();
                break;
            }
            default: {
                board = null;
                break;
            }
        }
        addPlayer(player);
        for (int i=0; i< this.numberOfAIPlayers; i++) {
            addPlayer(new EasyAIPlayer());
            numberOfConnectedPlayers++;
        }
        Server.getRoomList().add(this);
    }

    public void startGame(ClientHandler owner) {
        GameThread game = new GameThread(owner);
        humanPlayers.add(game);
        game.start();
    }

    public boolean addPlayer(Player player) {
        if (rules != null && board != null && players.size() < numberOfPlayers && !isAlreadyPlaying(player)) {
            player.setGameId(rules.setPlayerId(numberOfPlayers, players.size()));
            players.add(player);
            board.addPlayer(player);
            System.out.println("Player added. " + players.size() + " players now");
            return true;
        } else {
            return false;
        }
    }

    public String toString(){
        return (roomId + " " + numberOfConnectedPlayers + " " + numberOfPlayers);
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
                + board.getListOfColors(getPlayers().size())
                + gameMode + ";"
                + player.getGameId() + ";"
                + board.toString();
    }

    private boolean isAlreadyPlaying(Player player) {
        return players.contains(player);
    }
}
