package com.server.player;

import com.server.gameMode.BasicRules;
import com.server.gameMode.Rules;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    Player player;

    @BeforeEach
    void setUp() {
        player = new EasyAIPlayer(Rules.getRuleset("Basic", 5));
    }

    @Test
    void joinRoom() {
    }

    @Test
    void leaveRoom() {
    }

    @Test
    void leave() {
    }

    @Test
    void getPlayer() {
        try {
            assertTrue(Player.getPlayer("nick", new Socket()) instanceof HumanPlayer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getPlayer1() {
        assertTrue(Player.getPlayer("Easy", new BasicRules(3)) instanceof AIPlayer);
    }

    @Test
    void setGameId() {
        player.setGameId(4);
        assertEquals(4, player.getGameId());
    }

    @Test
    void getPlayerId() {
        int id = player.getPlayerId();
        player = new EasyAIPlayer(Rules.getRuleset("Basic", 5));
        assertEquals(id + 1, player.getPlayerId());
    }

    @Test
    void getRoomThreadById() {
    }

    @Test
    void isHuman() {
        assertFalse(player.isHuman());
    }
}