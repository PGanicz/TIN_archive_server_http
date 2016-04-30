package com.websocket.game.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class ActivateUser {

    @Id
    @GeneratedValue
    public long id;

    @ManyToOne
    public User user;

    public String activateString;

    public ActivateUser() {
    }

    public ActivateUser(User user, String activateString) {
        this.user = user;
        this.activateString = activateString;
    }
}
