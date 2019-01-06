package com.server.gameMode;

public abstract class Board {
    private BoardField[][] fieldArray;
    private static String colors[];

    public static Board getBoardType(String gameMode) {
        switch (gameMode) {
            case "Basic": {
                return new BasicBoard();
            }
            default: {
                return null;
            }
        }
    }

    public Board(int rows, int cols, String[] colors) {
        this.fieldArray = new BoardField[rows][cols];
        this.colors = colors;
    }

    public BoardField getField(int y, int x) {
        return this.fieldArray[y][x];
    }
    public abstract void addPlayer(int playerId, int gameId);
    public abstract String getListOfColors();

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

    public void printArray() {
        int y = fieldArray.length;
        int x = fieldArray[0].length;

        for (int i=0;i<y;i++){
            for (int j=0;j<x;j++) {
                if(fieldArray[i][j] != null) {
                    System.out.print(fieldArray[i][j].getOutputCode());
                }
                else {
                    System.out.print("-");
                }
            }
            System.out.println();
        }
    }

    public void movePawn(int y1, int x1, int y2, int x2){
        BoardField tempField = fieldArray[y2][x2];
        fieldArray[y2][x2] = fieldArray [y1][x1];
        fieldArray[y1][x1] = tempField;
    }
}
