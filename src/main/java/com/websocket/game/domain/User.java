package com.websocket.game.domain;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by piotr on 30.03.16.
 */

@Entity
public class User
{
    @Id
    @GeneratedValue
    public Long id;

    public String username;
    public String password;
    public String email;

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
