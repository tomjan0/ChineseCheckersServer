package com.server;

public class  BasicRules implements Rules {
    @Override
    public boolean checkIfWon(int playerId) {
        return false;
    }

    @Override
    public String showPossibleMoves(int playerId) {
        return null;
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
