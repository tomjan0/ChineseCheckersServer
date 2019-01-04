package com.server;

public interface Rules {
    String listOfGameModes = "Basic";
    boolean checkIfWon(Player player);
    String showPossibleMoves();
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
