package com.websocket.game.domain;

import org.springframework.web.socket.TextMessage;

/**
 * Created by piotr on 31.03.16.
 * This class provides more flexibility,
 * in the future we can have more complicated payloads (e.g JSON)
 */
public enum GameMessage
{
    INIT,
    JOIN,
    LEAVE,
    START,
    MOVE;
    public static GameMessage getMessage(TextMessage s)
    {
        GameMessage mes = null;
        switch(s.getPayload())
        {
            case "INIT": mes = INIT; break;
            case "JOIN": mes = JOIN; break;
            case "LEAVE":mes = LEAVE; break;
            case "START":mes = START; break;
            case "MOVE":mes = MOVE; break;
        }
        return mes;
    };
}
