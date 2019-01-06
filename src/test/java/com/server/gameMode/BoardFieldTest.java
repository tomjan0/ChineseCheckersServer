package com.server.gameMode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardFieldTest {

    private BoardField field;

    @BeforeEach
    void setUp() {
        field  = new BoardField();
    }

    @Test
    void getPawn() {
        assertNull(field.getPawn());

    }

    @Test
    void setPawn() {
        field.setPawn(new PlayerPawn(4));
        assertEquals("4", field.getOutputCode());
        field.setPawn(3);
        assertEquals("3", field.getOutputCode());
    }


    @Test
    void winnerId() {
        field.setWinnerId(7);
        assertEquals(7, field.getWinnerId());
    }


    @Test
    void removePawn() {
        field.removePawn();
        assertNull(field.getPawn());
        assertEquals("o", field.getOutputCode());
    }
}