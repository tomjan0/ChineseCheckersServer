package com.server.gameMode;

import java.util.ArrayList;

public class  BasicRules implements Rules {
    private BasicBoard board;
    private int [] availableIds;
    private String madeMoves = "";
    private int turn;
    private int skipsInRow;

    public BasicRules(int totalNumberOfPlayers) {
        board = new BasicBoard();
        if (totalNumberOfPlayers != 0) {
            this.availableIds = new int[totalNumberOfPlayers];
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
            }
        }
    }

    @Override
    public Board getBoard() {
        return board;
    }

    @Override
    public boolean checkIfWon() {
        return board.areAllInPlace(whoseTurn());
    }

    @Override
    public boolean anyMovesLeft() {
        for (int i = 0; i < board.getROWS(); i++) {
            for (int j = 0; j < board.getCOLS(); j++) {
                if (board.getField(i,j) != null) {
                    if (board.getField(i, j).getPawn() != null && !showPossibleMoves(i, j).isEmpty()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String showPossibleMoves(int y, int x) {

        StringBuilder result = new StringBuilder();
        boolean isFirstRun = true;
        PlayerPawn pawn = board.getField(y,x).getPawn();

        ArrayList<Integer> toCheck = new ArrayList<>();
        toCheck.add(0);
        toCheck.add(0);
        toCheck.add(y);
        toCheck.add(x);

        while (!toCheck.isEmpty()) {
            y = toCheck.get(2);
            x = toCheck.get(3);

            check(y, x - 1, y, x - 2, result, isFirstRun, toCheck, pawn);
            check(y, x + 1, y, x + 2, result, isFirstRun, toCheck, pawn);
            if (y % 2 == 0) {
                check(y - 1, x, y - 2, x - 1, result, isFirstRun, toCheck, pawn);
                check(y + 1, x, y + 2, x - 1, result, isFirstRun, toCheck, pawn);
                check(y - 1, x + 1, y - 2, x + 1, result, isFirstRun, toCheck, pawn);
                check(y + 1, x + 1, y + 2, x + 1, result, isFirstRun, toCheck, pawn);
            } else {
                check(y - 1, x, y - 2, x + 1, result, isFirstRun, toCheck, pawn);
                check(y + 1, x, y + 2, x + 1, result, isFirstRun, toCheck, pawn);
                check(y - 1, x - 1, y - 2, x - 1, result, isFirstRun, toCheck, pawn);
                check(y + 1, x - 1, y + 2, x - 1, result, isFirstRun, toCheck, pawn);
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


    private void check(int y1, int x1, int y2, int x2, StringBuilder result, boolean isFirstRun, ArrayList<Integer> toCheck, PlayerPawn pawn) {
        if(y2 != toCheck.get(0) || x2 != toCheck.get(1)) {
            if (board.getField(y1, x1) != null) {
                if (board.getField(y1, x1).getOutputCode().equals("o")) {
                    if(isFirstRun) {
                        if (pawn.isInPlace()) {
                            if (pawn.getOwnerId() == board.getField(y1,x1).getWinnerId()) {
                                result.append(y1).append(" ").append(x1).append(";");
                            }
                        } else {
                            result.append(y1).append(" ").append(x1).append(";");
                        }
                    }
                } else if (!result.toString().contains(y2 + " " + x2 + ";") && board.getField(y2, x2) != null && board.getField(y2, x2).getOutputCode().equals("o")) {
                    if(pawn.isInPlace()) {
                        if (pawn.getOwnerId() == board.getField(y2,x2).getWinnerId()) {
                            result.append(y2).append(" ").append(x2).append(";");
                            toCheck.add(toCheck.get(2));
                            toCheck.add(toCheck.get(3));
                            toCheck.add(y2);
                            toCheck.add(x2);
                        }
                    } else {
                        result.append(y2).append(" ").append(x2).append(";");
                        toCheck.add(toCheck.get(2));
                        toCheck.add(toCheck.get(3));
                        toCheck.add(y2);
                        toCheck.add(x2);
                    }
                }
            }
        }
    }


    @Override
    public int setPlayerId() {
        for (int i = 0; i < availableIds.length; i++) {
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
//                if (request.split(";").length == 1) {
//                    makeMove("pass");
//                } else {
                    makeMove(request.split(";")[1]);
//                }
                return "null";
            }
            default: {
                return "error;Operation not allowed";
            }
        }
    }

    @Override
    public void makeMove(String moves) {
        madeMoves = moves;
        if(!moves.equals("pass")) {
            String[] data = madeMoves.split(" ");
            int y1 = Integer.parseInt(data[0]);
            int x1 = Integer.parseInt(data[1]);
            int y2 = Integer.parseInt(data[2]);
            int x2 = Integer.parseInt(data[3]);
            board.movePawn(y1, x1, y2, x2);
            skipsInRow = 0;
        } else {
            skipsInRow++;
        }
    }

    @Override
    public String getMadeMoves() {
        return madeMoves;
    }

    @Override
    public int whoseTurn() {
        return turn;
    }

    @Override
    public void nextTurn() {
        int index;
        for (index = 0; index < availableIds.length; index++) {
            if (availableIds[index] == turn)    break;
        }
        index = (index + 1)%availableIds.length;
        turn = availableIds[index];
        System.out.println("Now player " + turn);
    }

    @Override
    public int whoStarts() {
        turn = availableIds[(int)(Math.random() * availableIds.length)];
        System.out.println("Starts player " + turn);
        return turn;
    }

    @Override
    public int howManySkipped() {
        return skipsInRow;
    }

    @Override
    public String getWinningCorner(int gameId, int which) {
        switch (gameId) {
            case 1: {
                switch (which) {
                    case 1: return "17 7";
                    case 2: return "16 6";
                    case 3: return "16 7";
                    case 4: return "15 6";
                    case 5: return "15 7";
                    case 6: return "15 8";
                    case 7: return "14 5";
                    case 8: return "14 6";
                    case 9: return "14 7";
                    case 10: return "14 8";

                }
            }
            case 2: {
                switch (which) {
                    case 1: return "13 1";
                    case 2: return "12 1";
                    case 3: return "13 2";
                    case 4: return "12 2";
                    case 5: return "11 2";
                    case 6: return "10 2";
                    case 7: return "13 3";
                    case 8: return "12 3";
                    case 9: return "11 3";
                    case 10: return "13 4";

                }
            }
            case 3: {
                switch (which) {
                    case 1: return "5 1";
                    case 2: return "6 1";
                    case 3: return "5 2";
                    case 4: return "6 2";
                    case 5: return "7 2";
                    case 6: return "8 2";
                    case 7: return "5 3";
                    case 8: return "6 3";
                    case 9: return "7 3";
                    case 10: return "5 4";

                }
            }
            case 4: {
                switch (which) {
                    case 1: return "1 7";
                    case 2: return "2 6";
                    case 3: return "2 7";
                    case 4: return "3 6";
                    case 5: return "3 7";
                    case 6: return "3 8";
                    case 7: return "4 5";
                    case 8: return "4 6";
                    case 9: return "4 7";
                    case 10: return "4 8";

                }
            }
            case 5: {
                switch (which) {
                    case 1: return "5 13";
                    case 2: return "5 12";
                    case 3: return "6 12";
                    case 4: return "7 12";
                    case 5: return "5 11";
                    case 6: return "6 11";
                    case 7: return "7 11";
                    case 8: return "8 11";
                    case 9: return "5 10";
                    case 10: return "6 10";

                }
            }
            case 6: {
                switch (which) {
                    case 1: return "13 13";
                    case 2: return "13 12";
                    case 3: return "12 12";
                    case 4: return "11 12";
                    case 5: return "13 11";
                    case 6: return "12 11";
                    case 7: return "11 11";
                    case 8: return "10 11";
                    case 9: return "13 10";
                    case 10: return "12 10";

                }
            }
            default: return "0 0";
        }
    }


}
