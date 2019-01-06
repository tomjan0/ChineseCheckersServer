package com.server.gameMode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    private Board board = new BasicBoard();

    @Test
    void getField() {
        assertEquals(board.getField(4,9), board.getFieldArray()[4][9]);
    }


    @Test
    void createField() {
        board.createField(4,5);
        assertNotNull(board.getField(4,5));
    }

    @Test
    void createField1() {
        board.createField(12,3,2);
        assertNotNull(board.getField(12,3));
        assertNotNull(board.getField(12,4));
    }

    @Test
    void getColor() {
        assertEquals("#000", board.getColor(154));
        assertEquals("#f00", board.getColor(0));
    }


    @Test
    void howManyInPlace() {
        board.addPlayer(2,3);
        board.movePawn(13,11,6,2);
        assertEquals(3, board.getField(6,2).getPawn().getOwnerId());
        assertEquals(3, board.getField(6,2).getWinnerId());
        assertTrue(board.getField(6,2).getPawn().isInPlace());
        assertEquals(1, board.howManyInPlace(3));
    }
}