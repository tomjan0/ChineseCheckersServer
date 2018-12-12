package com.server;

public class BasicBoard extends Board {
    public BasicBoard() {
        super(new BoardField[19][15]);
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
    public BoardField getField(int y, int x) {
        return this.fieldArray[y][x];
    }

    @Override
    public void addPlayer(Player player) {
        switch (player.getGameId()){
            case 1:
            {
                this.createField(1,7);
                this.createField(2,6,2);
                this.createField(3,6,3);
                this.createField(4,5,4);
            }break;
        }

    }
}
