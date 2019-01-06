package com.server;

import com.server.player.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private Player player;
    private BufferedReader in;
    private Socket clientSocket;

    public ClientHandler(Socket socket) {
        try {
            clientSocket = socket;
            System.out.println(clientSocket.getInetAddress().toString() + " Connected");
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            player = null;
        } catch (IOException e) {
            closeSocket();
        }
    }

    @Override
    public void run() {
        synchronized (this) {
            try {
                String message = in.readLine();
                do {
                    System.out.println("\t" + clientSocket.getInetAddress() +" Client request: " + message);
                    chooseResponse(message);
                    message = in.readLine();
                } while (message != null);
                closeSocket();
            } catch (IOException ex) {
                closeSocket();
            }
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

    public synchronized void chooseResponse(String request) {
        String requestCode = request.split(";")[0];
        switch (requestCode) {
            case "close": {
                closeSocket();
                break;
            }
            case "create-player": {
                try {
                    setPlayer(Player.getPlayer(request.split(";")[1], getClientSocket()));
                } catch (IOException e) {

                }
                System.out.println(getPlayer().toString());
                break;
            }
            case "current-room-list": {
                player.sendMessage(Server.getCurrentRoomList());
                break;
            }
            case "new-room-game-modes": {
                player.sendMessage(Server.getListOfGameModes());
                break;
            }
            case "new-room-capacity": {
                String capacityList = Server.getRoomCapacityList(request.split(";")[1]);
                if (capacityList != null) {
                    player.sendMessage(capacityList);
                } else {
                    player.sendMessage("error;This game mode is unavailable");
                }
                break;
            }
            case "create-new-room": {
                int roomId = Server.createRoom(request, this);
                player.sendMessage(Server.getRoom(roomId).getInfo(player));
                break;
            }
            case "join-room": {
                int roomId = Integer.parseInt(request.split(";")[1]);
                boolean result = player.joinRoom(roomId);
                if (result) {
                    player.sendMessage(Server.getRoom(roomId).getInfo(player));
                } else {
                    player.sendMessage("error;Room is not available for you");
                }
                break;
            }
            case "room-request": {
                int roomId = Integer.parseInt(request.split(";")[1]);
                if (player.getRoomThreadById(roomId) != null) {
                    String response = player.getRoomThreadById(Integer.parseInt(request.split(";")[1]))
                            .handleRequest(request);
                    if (response.equals("null"))    break;
                    if (response.split(";")[1].equals("success")) {
                        player.sendMessage(response);
                    } else {
                        player.sendMessage("error;Something went wrong");
                    }
                } else {
                    player.sendMessage("error;You are not in this room anymore");
                }
                break;
            }
            default: {
                player.sendMessage("error;Request " + requestCode + " isn't handled by server");
                break;
            }

        }
    }

    public void closeSocket() {
        try {
            in.close();
            clientSocket.close();
            if (player != null) {
                player.leave();
            }
            System.out.println(clientSocket.getInetAddress().toString() + " Disconnected");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
