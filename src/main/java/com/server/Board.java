package com.server;

public abstract class Board {
    BoardField[][] fieldArray;

    public Board(BoardField[][] fieldArray) {
        this.fieldArray = fieldArray;
    }

    public abstract BoardField getField(int y, int x);
    public void createField(int y, int x){
        fieldArray[y][x] = new BoardField();
    }
    public void createField(int y, int startX, int length){
        for (int i=0; i<length; i++){
            fieldArray[y][startX + i] = new BoardField();
        }
    }

    public abstract void addPlayer(Player player);
}
