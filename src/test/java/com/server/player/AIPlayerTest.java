package com.server.player;

import com.server.gameMode.BasicRules;
import com.server.gameMode.Rules;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AIPlayerTest {

    @Test
    void getAI() {
        assertNotNull(Player.getPlayer("Easy", Rules.getRuleset("Basic",3)));
        assertNull(Player.getPlayer("test", new BasicRules(5)));
    }
}