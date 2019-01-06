package com.server.gameMode;

public abstract class Board {
    private BoardField[][] fieldArray;
    private static String [] colors;
    private final int ROWS;
    private final int COLS;

    Board(int rows, int cols, String[] colors) {
        ROWS = rows;
        COLS = cols;
        this.fieldArray = new BoardField[ROWS][COLS];
        Board.colors = colors;
    }

    public BoardField getField(int y, int x) {
        return this.fieldArray[y][x];
    }
    public abstract void addPlayer(int playerId, int gameId);
    public abstract void removePlayer(int gameId);
    public abstract String getListOfColors();

    public int getROWS() {
        return ROWS;
    }

    public int getCOLS() {
        return COLS;
    }

    void createField(int y, int x){
        fieldArray[y][x] = new BoardField();
    }
    void createField(int y, int startX, int length){
        for (int i=0; i<length; i++){
            fieldArray[y][startX + i] = new BoardField();
        }
    }

    String getColor(int n) {
        if (n < colors.length) {
            return colors[n];
        } else {
            return "#000";
        }
    }

    BoardField[][] getFieldArray() {
        return fieldArray;
    }


    void movePawn(int y1, int x1, int y2, int x2){
        fieldArray[y2][x2].setPawn(fieldArray[y1][x1].getPawn());
        fieldArray[y1][x1].removePawn();
        if (fieldArray[y2][x2].getPawn().getOwnerId() == fieldArray[y2][x2].getWinnerId()){
            fieldArray[y2][x2].getPawn().setInPlace(true);
        }
    }

    public abstract boolean areAllInPlace(int gameId);

    int howManyInPlace(int gameId) {
        int inPlace = 0;
        for (int i = 0; i < getROWS(); i++) {
            for (int j = 0; j < getCOLS(); j++) {
                if (getField(i, j) != null && getField(i,j).getPawn() != null) {
                    if (getField(i,j).getPawn().getOwnerId() == gameId) {
                            if(getField(i,j).getWinnerId() == gameId) {
                                inPlace++;
                            }
                    }
                }
            }
        }
        return inPlace;
    }
}
