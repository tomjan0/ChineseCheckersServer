package com.server.gameMode;

import java.util.ArrayList;

public class  BasicRules implements Rules {
    private BasicBoard board;

    public BasicRules() {
        board = new BasicBoard();
    }

    @Override
    public Board getBoard() {
        return board;
    }

    @Override
    public boolean checkIfWon(int playerId) {
        return false;
    }

    @Override
    public String showPossibleMoves(int y, int x) {

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

    @Override
    public boolean isMyTurn(int playerId) {
        return false;
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
    public int setPlayerId(int totalNumberOfPlayers) {
        int [] availableIds = new int[totalNumberOfPlayers];
        switch (totalNumberOfPlayers) {
            case 2: {
                availableIds[0] = 1;
                availableIds[1] = 4;
                break;
            }
            case 3: {
                for (int i = 0; i < 3; i++) {
                    availableIds[i] = 2*i + 1;
                }
                break;
            }
            case 4: {
                availableIds[0] = 2;
                availableIds[1] = 3;
                availableIds[2] = 5;
                availableIds[3] = 6;
                break;
            }
            case 6: {
                for (int i = 0; i < 6; i++) {
                    availableIds[i] = i + 1;
                }
                break;
            }
            default: {
                return -1;
            }
        }
        for (int i = 0; i < totalNumberOfPlayers; i++) {
            if (!board.toString().contains(availableIds[i] + "")){
                return availableIds[i];
            }
        }
        return -1;
    }

    @Override
    public String getCapacityList() {
        return "2;3;4;6";
    }

    @Override
    public String handleRequest(String request) {
        String requestCode = request.split(";")[0];
        switch (requestCode) {
            case "possible-moves": {
                String[] data = request.split(";");
                int y = Integer.parseInt(data[1]);
                int x = Integer.parseInt(data[2]);
                String answer = showPossibleMoves(y,x);
                return "possible-moves;success;<" + answer;
            }
            case "move": {
                String[] data = request.split(";");
                int y1 = Integer.parseInt(data[1]);
                int x1 = Integer.parseInt(data[2]);
                int y2 = Integer.parseInt(data[3]);
                int x2 = Integer.parseInt(data[4]);
                board.movePawn(y1,x1,y2,x2);
                return "move;success";
            }
            default: {
                return "error;Operation not allowed";
            }
        }
    }
}
