package com.server;

public class BasicBoard extends Board {
    public BasicBoard() {
        super(19,15, new String[]{"#f00","#009","#090","#ff0","#f39","#606"});
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
    public void addPlayer(Player player) {
        switch (player.getGameId()){
            case 1:
            {
                this.setPlayer(1,7,1, player);
                this.setPlayer(2,6,2, player);
                this.setPlayer(3,6,3, player);
                this.setPlayer(4,5,4, player);
            }break;
            case 2:
            {
                this.setPlayer(8,11,1, player);
                this.setPlayer(7,11,2, player);
                this.setPlayer(6,10,3, player);
                this.setPlayer(5,10,4, player);
            }break;
            case 3:
            {
                this.setPlayer(10,11,1, player);
                this.setPlayer(11,11,2, player);
                this.setPlayer(12,10,3, player);
                this.setPlayer(13,10,4, player);
            }break;
            case 4:
            {
                this.setPlayer(17,7,1, player);
                this.setPlayer(16,6,2, player);
                this.setPlayer(15,6,3, player);
                this.setPlayer(14,5,4, player);
            }break;
            case 5:
            {
                this.setPlayer(10,2,1, player);
                this.setPlayer(11,2,2, player);
                this.setPlayer(12,1,3, player);
                this.setPlayer(13,1,4, player);
            }break;
            case 6:
            {
                this.setPlayer(8,2,1, player);
                this.setPlayer(7,2,2, player);
                this.setPlayer(6,1,3, player);
                this.setPlayer(5,1,4, player);
            }break;
        }

    }

    private void setPlayer(int y, int x, int length, Player player){
        for (int i=0; i < length; i++){
            getField(y,x+i).setPlayer(player);
        }
    }

    @Override
    public String toString() {
        String result = "";
        for (int i=1; i<18; i++) {
            if (i%2 == 0) {
                result += " ";
            }
            for (int j = 0; j < 15; j++) {
                if (getField(i,j) == null){
                    result += '-';
                } else if (getField(i,j).getPlayer() == null) {
                    result += 'o';
                } else {
                    result += String.valueOf(getField(i, j).getPlayer().getGameId());
                }
                result += " ";
            }
            result += "\\n";
        }
        return result;
    }

    @Override
    public String getListOfColors(int n) {
        StringBuilder list = new StringBuilder();
        for (int i = 0; i < n; i++) {
            list.append(super.getColor(i)).append(";");
        }
        return list.toString();
    }

}
