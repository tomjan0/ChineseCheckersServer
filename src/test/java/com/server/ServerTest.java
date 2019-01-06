package com.server;

import org.junit.jupiter.api.Test;

import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

class ServerTest {

    @Test
    void getCurrentRoomList() {
        assertNotNull(Server.getCurrentRoomList());
    }

}