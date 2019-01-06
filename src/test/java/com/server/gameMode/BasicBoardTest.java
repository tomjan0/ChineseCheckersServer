package com.server.gameMode;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BasicBoardTest {

    private Board board = new BasicBoard();

    @Test
    void addPlayer() {
        board.addPlayer(23,1);
        board.addPlayer(2,2);
        board.addPlayer(87,3);
        board.addPlayer(33,4);
        board.addPlayer(653,5);
        board.addPlayer(223,6);
        assertEquals(0, board.howManyInPlace(4));
    }

    @Test
    void removePlayer() {
        board.addPlayer(223,6);
        assertEquals(6,board.getField(7,2).getPawn().getOwnerId());
        board.removePlayer(6);
        assertNull(board.getField(7,2).getPawn());
    }


    @Test
    void areAllInPlace() {
        assertFalse(board.areAllInPlace(4));
    }
}