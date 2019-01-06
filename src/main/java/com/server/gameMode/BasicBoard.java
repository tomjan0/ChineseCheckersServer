package com.server.gameMode;

import java.util.ArrayList;

public class BasicBoard extends Board {
    public BasicBoard() {
        super(19,15, new String[]{"#f00","#009","#090","#f90","#f39","#606"});
        this.createField(1,7);
        this.createField(2,6,2);
        this.createField(3,6,3);
        this.createField(4,5,4);
        this.createField(5,1,13);
        this.createField(6,1,12);
        this.createField(7,2,11);
        this.createField(8,2,10);
        this.createField(9,3,9);
        this.createField(10,2,10);
        this.createField(11,2,11);
        this.createField(12,1,12);
        this.createField(13,1,13);
        this.createField(14,5,4);
        this.createField(15,6,3);
        this.createField(16,6,2);
        this.createField(17,7);
    }

    @Override
    public void addPlayer(int playerId, int gameId) {
        switch (gameId){
            case 1:
            {
                this.setPlayer(1,7,1, gameId);
                this.setPlayer(2,6,2, gameId);
                this.setPlayer(3,6,3, gameId);
                this.setPlayer(4,5,4, gameId);
                this.setWinner(17,7,1, gameId);
                this.setWinner(16,6,2, gameId);
                this.setWinner(15,6,3, gameId);
                this.setWinner(14,5,4, gameId);
            }break;
            case 2:
            {
                this.setPlayer(8,11,1, gameId);
                this.setPlayer(7,11,2, gameId);
                this.setPlayer(6,10,3, gameId);
                this.setPlayer(5,10,4, gameId);
                this.setWinner(10,2,1, gameId);
                this.setWinner(11,2,2, gameId);
                this.setWinner(12,1,3, gameId);
                this.setWinner(13,1,4, gameId);
            }break;
            case 3:
            {
                this.setPlayer(10,11,1, gameId);
                this.setPlayer(11,11,2, gameId);
                this.setPlayer(12,10,3, gameId);
                this.setPlayer(13,10,4, gameId);
                this.setWinner(8,2,1, gameId);
                this.setWinner(7,2,2, gameId);
                this.setWinner(6,1,3, gameId);
                this.setWinner(5,1,4, gameId);
            }break;
            case 4:
            {
                this.setPlayer(17,7,1, gameId);
                this.setPlayer(16,6,2, gameId);
                this.setPlayer(15,6,3, gameId);
                this.setPlayer(14,5,4, gameId);
                this.setWinner(1,7,1, gameId);
                this.setWinner(2,6,2, gameId);
                this.setWinner(3,6,3, gameId);
                this.setWinner(4,5,4, gameId);
            }break;
            case 5:
            {
                this.setPlayer(10,2,1, gameId);
                this.setPlayer(11,2,2, gameId);
                this.setPlayer(12,1,3, gameId);
                this.setPlayer(13,1,4, gameId);
                this.setWinner(8,11,1, gameId);
                this.setWinner(7,11,2, gameId);
                this.setWinner(6,10,3, gameId);
                this.setWinner(5,10,4, gameId);
            }break;
            case 6:
            {
                this.setPlayer(8,2,1, gameId);
                this.setPlayer(7,2,2, gameId);
                this.setPlayer(6,1,3, gameId);
                this.setPlayer(5,1,4, gameId);
                this.setWinner(10,11,1, gameId);
                this.setWinner(11,11,2, gameId);
                this.setWinner(12,10,3, gameId);
                this.setWinner(13,10,4, gameId);
            }break;
        }

    }

    private void setPlayer(int y, int x, int length, int gameId){
        for (int i=0; i < length; i++){
            getField(y,x+i).setPawn(gameId);
        }
    }
    private void setWinner(int y, int x, int length, int gameId){
        for (int i=0; i < length; i++){
            getField(y,x+i).setWinnerId(gameId);
        }
    }

    @Override
    public void removePlayer(int gameId) {
        for (int i = 0; i < super.getROWS(); i++) {
            for (int j = 0; j < super.getCOLS(); j++) {
                if (getField(i,j) != null && getField(i,j).getOutputCode().equals(gameId + "")) {
                    getField(i,j).removePawn();
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i=1; i<18; i++) {
            if (i%2 == 0) {
                result.append(" ");
            }
            for (int j = 0; j < 15; j++) {
                if (getField(i,j) == null){
                    result.append('-');
                } else {
                    result.append(getField(i, j).getOutputCode());
                }
                result.append(" ");
            }
            result.append("\\n");
        }
        return result.toString();
    }

    @Override
    public String getListOfColors() {
        StringBuilder list = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            list.append(super.getColor(i)).append(";");
        }
        return list.toString();
    }


    @Override
    public boolean areAllInPlace(int gameId) {
        return howManyInPlace(gameId) == 10;
    }
}
