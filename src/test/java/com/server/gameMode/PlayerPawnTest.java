package com.server.gameMode;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerPawnTest {

    @Test
    void getOwnerId() {
        PlayerPawn pawn  = new PlayerPawn(7);
        assertEquals(7, pawn.getOwnerId());
    }

    @Test
    void pawnInPlace() {
        PlayerPawn pawn = new PlayerPawn(6);
        assertFalse(pawn.isInPlace());
        pawn.setInPlace(true);
        assertTrue(pawn.isInPlace());
    }

}