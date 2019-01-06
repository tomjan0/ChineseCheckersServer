package com.server;

import com.server.gameMode.Rules;
import com.server.player.EasyAIPlayer;
import com.server.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoomTest {
    Room room = new Room();
    Player player = new EasyAIPlayer(Rules.getRuleset("Basic", 2));
    Player ai = new EasyAIPlayer(Rules.getRuleset("Basic", 2));

    @BeforeEach
    void setUp() {
        Server.getRoomList().add(room);
        room.customizeRoom(player, 3,1, "Basic");
    }

    @Test
    void customizeRoom() {
        assertEquals("Basic", room.getGameMode());
    }

    @Test
    void addPlayer() {
        assertEquals(2, room.getPlayers().size());
        room.addPlayer(ai);
        assertEquals(3, room.getPlayers().size());
        room.removePlayer(ai.getPlayerId());
        assertEquals(2, room.getPlayers().size());
    }

    @Test
    void anyHumansLeft() {
        assertFalse(room.anyHumansLeft());
    }

    @Test
    void howManyHumansPlaying() {
        assertEquals(0, room.howManyHumansPlaying());
    }

    @Test
    void howManyPlaying() {
        assertEquals(2, room.howManyPlaying());
    }


    @Test
    void getRoomId() {
        Room newRoom = new Room();
        assertEquals(room.getRoomId() + 1, newRoom.getRoomId());
    }

    @Test
    void getPlayers() {
        room.addPlayer(player);
        assertEquals(player, room.getPlayers().get(0));
    }

    @Test
    void isGameOn() {
        assertFalse(room.isGameOn());
        room.setGameOn(true);
        assertTrue(room.isGameOn());
    }

    @Test
    void getPlayerById() {
        room.addPlayer(player);
        assertEquals(player, room.getPlayerById(player.getPlayerId()));
    }

    @Test
    void isFull() {
        assertFalse(room.isFull());
    }

    @Test
    void getPlayerByGameId() {
        room.addPlayer(player);
        player.setGameId(3);
        assertEquals(player, room.getPlayerByGameId(3));
    }

    @Test
    void isEveryoneReady() {
        room.addReadyPlayer();
        assertFalse(room.isEveryoneReady());
    }

}