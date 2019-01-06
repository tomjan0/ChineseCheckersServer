package com.server.gameMode;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BasicRulesTest {


    BasicBoard board = new BasicBoard();
    Rules rules = new BasicRules(6);


    @Test
    void checkIfWon() {
        board.addPlayer(46,6);
    }

    @Test
    void anyMovesLeft() {
        assertFalse(rules.anyMovesLeft());
    }


    @Test
    void handleRequest() {
        board.createField(7,8);
        assertEquals("null", rules.handleRequest("move;pass"));
    }

    @Test
    void getMadeMoves() {
        rules.makeMove("pass");
        assertEquals("pass", rules.getMadeMoves());
    }

    @Test
    void nextTurn() {
        rules = new BasicRules(2);
        int turn  = rules.whoStarts();
        rules.nextTurn();
        if (turn == 1)  assertEquals(4, rules.whoseTurn());
        else assertEquals(1, rules.whoseTurn());
    }

    @Test
    void whoStarts() {
        assertNotSame(rules.whoStarts(), rules.whoStarts());
    }

    @Test
    void howManySkipped() {
        rules.makeMove("pass");
        rules.makeMove("pass");
        rules.makeMove("pass");
        assertEquals(3, rules.howManySkipped());
    }

    @Test
    void getWinningCorner() {
    }
}