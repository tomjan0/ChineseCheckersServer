package com.server;

public abstract class Board {
    BoardField[][] fieldArray;

    public Board(int rows, int cols) {
        this.fieldArray = new BoardField[rows][cols];
    }

    public BoardField getField(int y, int x) {
        return this.fieldArray[y][x];
    }
    public abstract void addPlayer(Player player);

    public void createField(int y, int x){
        fieldArray[y][x] = new BoardField();
    }
    public void createField(int y, int startX, int length){
        for (int i=0; i<length; i++){
            fieldArray[y][startX + i] = new BoardField();
        }
    }

}
