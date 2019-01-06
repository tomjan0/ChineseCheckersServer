package com.server.gameMode;

public class  BasicRules implements Rules {
    @Override
    public boolean checkIfWon(int playerId) {
        return false;
    }

    @Override
    public String showPossibleMoves(int y, int x, Board board) {

        StringBuilder result = new StringBuilder();

        BoardField field;

        if ((field = board.getField(y,x-1)) != null) {
            if (field.getOutputCode() == "o") {
                result.append(y + " " + (x-1) + ";");
            } else if((field = board.getField(y,x-2)) != null && field.getOutputCode() == "o") {
                result.append((y) + " " + (x-2) + ";");
            }
        }
        if ((field = board.getField(y,x+1)) != null) {
            if (field.getOutputCode() == "o") {
                result.append(y + " " + (x + 1) + ";");
            } else if((field = board.getField(y,x+2)) != null && field.getOutputCode() == "o") {
                result.append((y) + " " + (x+2) + ";");
            }
        }

        if(y%2 == 0){
            if((field = board.getField(y-1,x)) != null) {
                if (field.getOutputCode() == "o") {
                    result.append((y - 1) + " " + x + ";");
                } else if((field = board.getField(y-2,x-1)) != null && field.getOutputCode() == "o") {
                    result.append((y-2) + " " + (x-1) + ";");
                }
            }
            if ((field = board.getField(y+1,x)) != null) {
                if (field.getOutputCode() == "o") {
                    result.append((y+1) + " " + x + ";");
                } else if((field = board.getField(y+2,x-1)) != null && field.getOutputCode() == "o") {
                    result.append((y+2) + " " + (x-1) + ";");
                }
            }
            if ((field = board.getField(y-1,x+1)) != null) {
                if (field.getOutputCode() == "o") {
                    result.append((y-1) + " " + (x+1) + ";");
                } else if((field = board.getField(y-2,x+1)) != null && field.getOutputCode() == "o") {
                    result.append((y-2) + " " + (x+1) + ";");
                }
            }

            if ((field = board.getField(y+1,x+1)) != null) {
                if (field.getOutputCode() == "o") {
                    result.append((y+1) + " " + (x+1) + ";");
                } else if((field = board.getField(y+2,x+1)) != null && field.getOutputCode() == "o") {
                    result.append((y+2) + " " + (x+1) + ";");
                }
            }
        } else {
            if((field = board.getField(y-1,x)) != null) {
                if (field.getOutputCode() == "o") {
                    result.append((y-1) + " " + x + ";");
                } else if((field = board.getField(y-2,x+1)) != null && field.getOutputCode() == "o") {
                    result.append((y-2) + " " + (x+1) + ";");
                }
            }
            if ((field = board.getField(y+1,x)) != null) {
                if (field.getOutputCode() == "o") {
                    result.append((y + 1) + " " + x + ";");
                } else if((field = board.getField(y+2,x+1)) != null && field.getOutputCode() == "o") {
                    result.append((y+2) + " " + (x+1) + ";");
                }
            }
            if ((field = board.getField(y-1,x-1)) != null) {
                if (field.getOutputCode() == "o") {
                    result.append((y - 1) + " " + (x - 1) + ";");
                } else if((field = board.getField(y-2,x-1)) != null && field.getOutputCode() == "o") {
                    result.append((y-2) + " " + (x-1) + ";");
                }
            }
            if ((field = board.getField(y+1,x-1)) != null) {
                if (field.getOutputCode() == "o") {
                    result.append((y+1) + " " + (x-1) + ";");
                } else if((field = board.getField(y + 2,x-1)) != null && field.getOutputCode() == "o") {
                    result.append((y+2) + " " + (x-1) + ";");
                }
            }
        }
        return result.toString();
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
