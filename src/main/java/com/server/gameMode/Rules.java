package com.server.gameMode;

public interface Rules {
    String listOfGameModes = "Basic";
    boolean checkIfWon(int playerId);
    String showPossibleMoves(int y, int x, Board board);
    int setPlayerId(int totalNumberOfPlayers, int loggedInPlayers);
    String getCapacityList();
    static Rules getRuleset(String mode) {
        switch (mode) {
            case "Basic": {
                return new BasicRules();
            }
            default: {
                return null;
            }
        }
    }
}
