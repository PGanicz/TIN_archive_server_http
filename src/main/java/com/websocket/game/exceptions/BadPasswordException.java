package com.websocket.game.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class BadPasswordException extends Exception {

    public BadPasswordException(String s) {
        super(s);
    }
}
