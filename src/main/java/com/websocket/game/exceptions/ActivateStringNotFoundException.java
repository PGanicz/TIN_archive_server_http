package com.websocket.game.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ActivateStringNotFoundException extends Exception {

    public ActivateStringNotFoundException(String s) {
        super(s);
    }
}
