package com.server;

public interface Rules {
    boolean checkIfWon(Player player);
    String showPossibleMoves();
    static String respond(String request) {
        String requestCode = request.split(";")[0];
        switch (requestCode){
            case "current-room-list": {
                //TODO: call method that returns string with current room list
                return "33 4 6;12 1 3";
            }
            case "create-player": {
                //TODO: call method that creates new player and returns his id
                return "564";
            }
            case "new-room-data": {
                //TODO: call method that returns current options for creating room
                return "1;4;3;Basic;2;3;4;6;1;2;3";
            }
            case "create-new-room": {
                //TODO: call method that creates new room and the other that returns info about room based on its id
                return "error;Request not supported yet";
            }
            case "join-room": {
                //TODO: call method that returns info about room based on id
                return "error;Request not supported yet";
            }
            default: {
                return "error;Request " + requestCode + " isn't handled by server";
            }
        }
    }

}
