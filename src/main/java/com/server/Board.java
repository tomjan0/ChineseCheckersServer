package com.server;

public abstract class Board {
    private BoardField[][] fieldArray;
    private static String colors[];

    public Board(int rows, int cols, String[] colors) {
        this.fieldArray = new BoardField[rows][cols];
        this.colors = colors;
    }

    public BoardField getField(int y, int x) {
        return this.fieldArray[y][x];
    }
    public abstract void addPlayer(Player player);
    public abstract String getListOfColors(int n);

    public void createField(int y, int x){
        fieldArray[y][x] = new BoardField();
    }
    public void createField(int y, int startX, int length){
        for (int i=0; i<length; i++){
            fieldArray[y][startX + i] = new BoardField();
        }
    }

    public String getColor(int n) {
        if (n < colors.length) {
            return colors[n];
        } else {
            return "#000";
        }
    }
}
