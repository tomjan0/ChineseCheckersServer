package com.server.gameMode;

import java.util.ArrayList;

public class  BasicRules implements Rules {
    @Override
    public boolean checkIfWon(int playerId) {
        return false;
    }

    @Override
    public String showPossibleMoves(int y, int x, Board board) {

        StringBuilder result = new StringBuilder();
        boolean isFirstRun = true;
        ArrayList<Integer> toCheck = new ArrayList<>();
        toCheck.add(0);
        toCheck.add(0);
        toCheck.add(y);
        toCheck.add(x);

        while (!toCheck.isEmpty()) {
            y = toCheck.get(2);
            x = toCheck.get(3);

            check(y, x - 1, y, x - 2, board, result, isFirstRun, toCheck);
            check(y, x + 1, y, x + 2, board, result, isFirstRun, toCheck);
            if (y % 2 == 0) {
                check(y - 1, x, y - 2, x - 1, board, result, isFirstRun, toCheck);
                check(y + 1, x, y + 2, x - 1, board, result, isFirstRun, toCheck);
                check(y - 1, x + 1, y - 2, x + 1, board, result, isFirstRun, toCheck);
                check(y + 1, x + 1, y + 2, x + 1, board, result, isFirstRun, toCheck);
            } else {
                check(y - 1, x, y - 2, x + 1, board, result, isFirstRun, toCheck);
                check(y + 1, x, y + 2, x + 1, board, result, isFirstRun, toCheck);
                check(y - 1, x - 1, y - 2, x - 1, board, result, isFirstRun, toCheck);
                check(y + 1, x - 1, y + 2, x - 1, board, result, isFirstRun, toCheck);
            }
            if (isFirstRun) {
                isFirstRun = false;
            }
            toCheck.remove(3);
            toCheck.remove(2);
            toCheck.remove(1);
            toCheck.remove(0);
        }

        return result.toString();
    }

    private void check(int y1, int x1, int y2, int x2, Board board, StringBuilder result, boolean isFirstRun, ArrayList<Integer> toCheck) {
        BoardField field;

        if(y2 != toCheck.get(0) && x2 != toCheck.get(1)) {
            if ((field = board.getField(y1, x1)) != null) {
                if (field.getOutputCode().equals("o")) {
                    if(isFirstRun) {
                        result.append(y1).append(" ").append(x1).append(";");
                    }
                } else if ((field = board.getField(y2, x2)) != null && field.getOutputCode().equals("o")) {
                    result.append(y2).append(" ").append(x2).append(";");
                    toCheck.add(toCheck.get(2));
                    toCheck.add(toCheck.get(3));
                    toCheck.add(y2);
                    toCheck.add(x2);
                }
            }
        }
    }


    @Override
    public int setPlayerId(int totalNumberOfPlayers, int loggedInPlayers) {
        switch (totalNumberOfPlayers) {
            case 2: {
                return loggedInPlayers == 0 ? 1 : 4;
            }
            case 3: {
                return 2*loggedInPlayers + 1;
            }
            case 4: {
                if (loggedInPlayers == 0)   return 2;
                if (loggedInPlayers == 1)   return 3;
                if (loggedInPlayers == 2)   return 5;
                if (loggedInPlayers == 3)   return 6;
            }
            case 6: {
                return loggedInPlayers + 1;
            }
            default: {
                return -1;
            }
        }
    }

    @Override
    public String getCapacityList() {
        return "2;3;4;6";
    }
}
