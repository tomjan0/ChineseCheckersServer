package com.server;

//import test.TestGUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private Player player;
    private PrintWriter out;
    private BufferedReader in;
    private Socket clientSocket;

    public ClientHandler(Socket socket) {
        try {
            clientSocket = socket;
            System.out.println(clientSocket.getInetAddress().toString() + " Connected");
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            player = null;
        } catch (IOException e) {
            closeSocket();
        }
    }

    @Override
    public void run() {
        try {
            String message = in.readLine();
            do {
                System.out.println("\t" + clientSocket.getInetAddress() +" Client request: " + message);
                chooseResponse(message);
                message = in.readLine();
            } while (message != null);
        } catch (IOException ex) {
//                System.out.println("Exception");
        }
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    private void chooseResponse(String request) {
        String requestCode = request.split(";")[0];
        switch (requestCode) {
            case "close": {
                closeSocket();
                break;
            }
            case "create-player": {
                //TODO: call method that creates new player and returns his id
                setPlayer(new HumanPlayer(request.split(";")[1], getClientSocket()));
                out.println(getPlayer().getPlayerId());
                System.out.println(getPlayer().toString());
                break;
            }
            case "current-room-list": {
                //TODO: call method that returns string with current room list
                StringBuilder responseB = new StringBuilder();
                for (Room room : Server.getRoomList()) {
                    responseB.append(room.toString());
                    responseB.append(";");
                }
                out.println(responseB);
                break;
            }
            case "new-room-game-modes": {
                //TODO: call method that returns current options for creating room
                out.println(Rules.listOfGameModes);
                break;
            }
            case "new-room-capacity": {
                Rules rules = Rules.getRuleset(request.split(";")[1]);
                if (rules != null) {
                    out.println(rules.getCapacityList());
                } else {
                    out.println("error;This game mode is unavailable");
                }
                break;
            }
            case "create-new-room": {
                //TODO: call method that creates new room and the other that returns info about room based on its id

                String[] data = request.split(";");
                String gameMode = data[1];
                int playersNo = Integer.parseInt(data[2]);
                int aiNo = Integer.parseInt(data[3]);
                Room room = new Room(player, playersNo, aiNo, gameMode);
                room.startGame(this);
                out.println(room.getInfo(player));
                break;
            }
            case "join-room": {
                //TODO: call method that returns info about room based on id
                int roomId = Integer.parseInt(request.split(";")[1]);
                Room room = Server.getRoom(roomId);
                if (room == null) {
                    out.println("error;Room deleted");
                }else if (room.addPlayer(getPlayer())) {
                    room.startGame(this);
                    out.println(room.getInfo(player));
                } else {
                    out.println("error;Room is not available for you");
                }
                break;
            }
            default: {
                out.println("error;Request " + requestCode + " isn't handled by server");
                break;
            }

        }
    }

    public void closeSocket() {
        try {
            clientSocket.close();
            System.out.println(clientSocket.getInetAddress().toString() + " Disconnected");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
