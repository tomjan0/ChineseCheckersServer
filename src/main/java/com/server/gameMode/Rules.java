package com.server.gameMode;


public interface Rules {
    String listOfGameModes = "Basic";
    static Rules getRuleset(String mode, int numberOfPlayers) {
        switch (mode) {
            case "Basic": {
                return new BasicRules(numberOfPlayers);
            }
            default: {
                return null;
            }
        }
    }
    boolean checkIfWon();
    boolean anyMovesLeft();
    String showPossibleMoves(int y, int x);
    int setPlayerId();
    String getCapacityList();
    Board getBoard();
    String handleRequest(String request);
    String getMadeMoves();
    void makeMove(String moves);
    int whoseTurn();
    void nextTurn();
    int whoStarts();
    String getWinningCorner(int gameId, int which);
    int howManySkipped();
}
