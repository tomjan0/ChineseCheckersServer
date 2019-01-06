package com.server;

import com.server.gameMode.Rules;
import com.server.player.Player;

import java.util.ArrayList;

public class Room {
    private static int roomCounter = 1;
    private int roomId;
    private ArrayList<Player> players;
    private int numberOfPlayers;
    private String gameMode;
    private Rules rules;
    private boolean isGameOn;
    private int numberOfReadyPlayers;
    private boolean gameFinished;

    public Room() {
        this.roomId = roomCounter;
        roomCounter++;
        this.players = new ArrayList<>();
        isGameOn = false;
        numberOfReadyPlayers = 0;
        gameFinished = false;
    }

    public void customizeRoom(Player player, int numberOfPlayers, int numberOfAiPlayers, String gameMode){
        this.numberOfPlayers = numberOfPlayers;
        this.gameMode = gameMode;
        this.rules = Rules.getRuleset(gameMode, numberOfPlayers);
        player.joinRoom(roomId);
        for (int i = 0; i < numberOfAiPlayers; i++) {
            Player.getPlayer("Easy", rules).joinRoom(roomId);
        }
        numberOfReadyPlayers = numberOfAiPlayers;
    }

    public boolean addPlayer(Player player) {
        if (rules != null && !isFull() && !isAlreadyPlaying(player) && !isGameOn() && !gameFinished) {
            player.setGameId(rules.setPlayerId());
            players.add(player);
            sortPlayersByGameId();
            rules.getBoard().addPlayer(player.getPlayerId(), player.getGameId());
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
            rules.getBoard().removePlayer(player.getGameId());
            player.setGameId(-1);
            System.out.println("Player left. Room #" + roomId + " has " + players.size() + " players now");
            return true;
        } else {
            return false;
        }

    }

    public boolean anyHumansLeft() {
        int humans = 0;
        for (Player player :
                players) {
            if (player.isHuman()) {
                humans++;
            }
        }
        return humans > 0;
    }
    public int howManyHumansPlaying() {
        int humans = 0;
        for (Player player :
                players) {
            if (player.isHuman() && player.getGameId() > 0) {
                humans++;
            }
        }
        return humans;
    }

    int howManyPlaying() {
        int howMany = 0;
        for(Player player : players) {
            if(player.getGameId() > 0) {
                howMany++;
            }
        }
        return howMany ;
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
                + "Welcome to new room!;"
                + getListOfPlayers() + ";"
                + rules.getBoard().getListOfColors()
                + gameMode + ";"
                + player.getGameId() + ";"
                + rules.getBoard().toString();
    }

    private boolean isAlreadyPlaying(Player player) {
        return players.contains(player);
    }

    public static String getListOfGameModes() {
        return Rules.listOfGameModes;
    }

    public static String getCapacityList(String gameMode) {
        Rules rules = Rules.getRuleset(gameMode, 0);
        if (rules != null) {
            return rules.getCapacityList();
        } else {
            return null;
        }
    }


    public boolean isGameOn() {
        return isGameOn;
    }

    public void setGameOn(boolean gameOn) {
        if (gameOn) {
            System.out.println("Game in room #" + getRoomId() + " has started");
        } else {
            System.out.println("Game in room #" + getRoomId() + " has finished");
            sendUpdateToEveryone("game-over");
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
        return rules.getBoard().toString();
    }



    public Rules getRules() {
        return rules;
    }

    public Player getPlayerByGameId(int gameId) {
        for (Player player :
                players) {
            if (player.getGameId() == gameId) {
                return player;
            }
        }
        return null;
    }

    private void sortPlayersByGameId() {
        ArrayList<Player> sortedPlayers = new ArrayList<>();
        for (int i = 1; i <= 6; i ++) {
            Player player = getPlayerByGameId(i);
            if (player != null) {
                sortedPlayers.add(player);
            }
        }
        players = sortedPlayers;
    }

    public boolean isEveryoneReady() {
        return numberOfReadyPlayers == numberOfPlayers;
    }

    public void addReadyPlayer() {
        this.numberOfReadyPlayers++;
    }

    public void startGame() {
        addReadyPlayer();
        if (isEveryoneReady()) {
            setGameOn(true);
            int whoStarts = rules.whoStarts();
            for (Player player :
                    players) {
                player.sendMessage("full-room;<" + getInfo(player)
                                + " <Game starts " + getPlayerByGameId(whoStarts).toString());
            }
            getPlayerByGameId(rules.whoseTurn()).sendMessage("your-turn;" + roomId);
        }
    }

    public void endTurn() {
        sendGameUpdate("update-board;" + rules.getMadeMoves(), rules.whoseTurn());
        String previous;
        if(getPlayerByGameId(rules.whoseTurn()) != null) {
            previous = getPlayerByGameId(rules.whoseTurn()).toString();
        } else {
            previous = "Player who left";
        }
        if (rules.checkIfWon()) {
            sendUpdateToEveryone("update-info;" + getPlayerByGameId(rules.whoseTurn()).toString() + " won");
            getPlayerByGameId(rules.whoseTurn()).sendMessage("you-won");
            getPlayerByGameId(rules.whoseTurn()).setGameId(-2);
        }
        if (howManyPlaying() > 1 && howManyHumansPlaying() > 0 && rules.howManySkipped() < howManyPlaying() && rules.anyMovesLeft()) {
            do {
                rules.nextTurn();
            } while (getPlayerByGameId(rules.whoseTurn()) == null);
            sendUpdateToEveryone("update-info;" + previous + " made move. Now " + getPlayerByGameId(rules.whoseTurn()).toString() + " is moving");
            getPlayerByGameId(rules.whoseTurn()).sendMessage("your-turn;" + roomId);
        } else {
            setGameOn(false);
            sendUpdateToEveryone("game-over");
        }
    }

    private void sendGameUpdate(String update, int gameId) {
        for (Player player :
                players) {
            if (player.getGameId() != gameId) {
                player.sendMessage(update);
            }
        }
    }

    private void sendUpdateToEveryone(String update) {
        for (Player player :
                players) {
            player.sendMessage(update);
        }
    }
}
