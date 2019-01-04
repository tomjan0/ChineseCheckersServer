package com.server;

public interface Rules {
    boolean checkIfWon(Player player);
    String showPossibleMoves();
    static String respond(String request, ServerThread client) {
        String requestCode = request.split(";")[0];
        switch (requestCode){
            case "current-room-list": {
                //TODO: call method that returns string with current room list
                StringBuilder responseB = new StringBuilder();
                for (Room room : Server.getRoomList()) {
                    responseB.append(room.toString());
                    responseB.append(";");
                }
//                responseB.append("33 4 6;12 1 3");

                return responseB.toString();
//                return "33 4 6;12 1 3";
            }
            case "create-player": {
                //TODO: call method that creates new player and returns his id
                Player player = new HumanPlayer(Server.getPlayerList().size(), client.getClientSocket(), request.split(";")[1]);
                Server.getPlayerList().add(player);
                client.setPlayer(player);

                return player.getPlayerId() + "";
//                return "564";
            }
            case "new-room-data": {
                //TODO: call method that returns current options for creating room
                return "1;4;3;Basic;2;3;4;6;1;2;3";
            }
            case "create-new-room": {
                //TODO: call method that creates new room and the other that returns info about room based on its id

                String[] data = request.split(";");
                int id = Server.getPlayerList().size();
                int playersNo = Integer.parseInt(data[2]);
                int aiNo = Integer.parseInt(data[3]);
                Player player = client.getPlayer();
                Room room = new Room(id, player, playersNo, aiNo);
                Server.getRoomList().add(room);
                Board board = new BasicBoard();
                player.setGameId(1);
                board.addPlayer(player);

                return id + ";Created by "+ player.getName() +";1;" + player.getName() + ";green;Basic;" + player.getGameId() + ";" + board.toString();
//                return room.toString();
//                return "error;Request not supported yet";
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
