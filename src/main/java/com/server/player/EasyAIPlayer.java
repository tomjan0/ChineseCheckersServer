package com.server.player;

import com.server.gameMode.Rules;


public class EasyAIPlayer extends AIPlayer {
    private int which;

    public EasyAIPlayer(Rules rules) {
        super("Easy Opponent", rules);
        which = 1;
    }

    @Override
    public boolean sendMessage(String message) {
        if (message.split(";")[0].equals("your-turn")) {
            makeMove(Integer.parseInt(message.split(";")[1]));
        }
        return true;
    }

    @Override
    protected void makeMove(int roomId) {

//        System.out.println(getGameId());
        int[][] cords = new int[10][2];
        int pos = 0;
        for (int i=0;i<rules.getBoard().getROWS(); i++) {
            for (int j = 0; j<rules.getBoard().getCOLS(); j++) {
                if (rules.getBoard().getField(i, j) != null && rules.getBoard().getField(i,j).getPawn() != null) {
                    if (rules.getBoard().getField(i,j).getPawn().getOwnerId() == getGameId()) {
                        cords[pos][0] = i;
                        cords[pos][1] = j;
//                        System.out.println(i + " " + j);
                        pos++;
                    }
                }
            }
        }

        String[] posMoves = new String[10];

        for (int i = 0; i < 10; i++) {
//            String tempMoves = rules.showPossibleMoves(cords[i][0], cords[i][1]);
//            System.out.println(tempMoves);
//            System.out.println("XDD");
           posMoves[i] = rules.showPossibleMoves(cords[i][0], cords[i][1]);
        }

        String[] winCorner = rules.getWinningCorner(getGameId(), which).split(" ");
        int winCornerY = Integer.parseInt(winCorner[0]);
        int winCornerX = Integer.parseInt(winCorner[1]);

        System.out.println(winCornerX + " " + winCornerY);

        double maxdiff = 0;
        int maxy = 0;
        int maxx = 0;
        int cordsId = 0;

        int count = 1;

        while (count > 0) {
            winCorner = rules.getWinningCorner(getGameId(), which).split(" ");
            winCornerY = Integer.parseInt(winCorner[0]);
            winCornerX = Integer.parseInt(winCorner[1]);
            if(rules.getBoard().getField(winCornerY,winCornerX) != null && rules.getBoard().getField(winCornerY,winCornerX).getPawn() != null) {
                if (rules.getBoard().getField(winCornerY,winCornerX).getPawn().getOwnerId() == getGameId()) {
                    which++;
                    count++;
                }
            }
            count--;
        }

        for (int i = 0; i < 10; i++) {
            if(!posMoves[i].isEmpty()) {
                boolean good = true;
                for (int j = 1; j < which; j++) {
                    String[] field = rules.getWinningCorner(getGameId(), j).split(" ");
                    int fieldY = Integer.parseInt(field[0]);
                    int fieldX = Integer.parseInt(field[1]);
                    if (cords[i][0] == fieldY && cords[i][1] == fieldX) {
                        good = false;
                    }
                }
                if(good) {
                    int besty = cords[i][0];
                    int bestx = cords[i][1];
                    double bestdiff = Math.sqrt(Math.pow(besty - winCornerY, 2) + Math.pow(bestx - winCornerX, 2));
                    String[] pairs = posMoves[i].split(";");
                    for (int j = 0; j < pairs.length; j++) {
                        String[] pair = pairs[j].split(" ");
                        int y = Integer.parseInt(pair[0]);
                        int x = Integer.parseInt(pair[1]);

                        double diff = Math.sqrt(Math.pow(Math.abs(y - winCornerY), 2) + Math.pow(Math.abs(x - winCornerX), 2));

//                    if (Math.abs(y - winCornerY) < Math.abs(besty - winCornerY) || Math.abs(x - winCornerX) < Math.abs(bestx - winCornerX)) {
//                        if (which == 10) {
//                            if (diff <= bestdiff) {
//                                bestx = x;
//                                besty = y;
//                            }
//                        }
                        if (diff < bestdiff) {
                            bestx = x;
                            besty = y;
                        }
//                    }
                    }
//                    if(which<10) {
                        double diff = Math.sqrt(Math.pow(Math.abs(besty - cords[i][0]), 2) + Math.pow(Math.abs(bestx - cords[i][1]), 2));
                        if (diff >= maxdiff) {
                            maxdiff = diff;
                            maxy = besty;
                            maxx = bestx;
                            cordsId = i;
                        }
//                    }
                }
            }
        }

        System.out.println(which);
        System.out.println(cords[cordsId][0] + " " + cords[cordsId][1]);
        if (maxdiff > 0) {
            if(maxy == winCornerY && maxx == winCornerX) {
                which++;
            }
            rules.makeMove(cords[cordsId][0] + " " + cords[cordsId][1] + " " + maxy + " " + maxx);
        } else {
            rules.makeMove("pass");
        }
        getRoomThreadById(roomId).getRoom().endTurn();

    }

}
