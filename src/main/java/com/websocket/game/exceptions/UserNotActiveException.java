package com.websocket.game.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class UserNotActiveException extends AuthenticationException {

    public UserNotActiveException(String s) {
        super(s);
    }
}
