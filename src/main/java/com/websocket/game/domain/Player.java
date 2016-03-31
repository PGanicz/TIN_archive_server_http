package com.websocket.game.domain;

import org.springframework.web.socket.WebSocketSession;

/**
 * Created by piotr on 31.03.16.
 */
public class Player
{
    private User user;
    private WebSocketSession session;

    @Override
    public String toString() {
        return "Player{" +
                "user=" + user +
                ", session=" + session +
                '}';
    }

    public Player(User user, WebSocketSession session) {
        this.user = user;
        this.session = session;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public WebSocketSession getSession() {
        return session;
    }

    public void setSession(WebSocketSession session) {
        this.session = session;
    }
}
