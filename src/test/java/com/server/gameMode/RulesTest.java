package com.server.gameMode;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RulesTest {

    @Test
    void getRuleset() {
        assertNull(Rules.getRuleset("none", 17));
    }
}