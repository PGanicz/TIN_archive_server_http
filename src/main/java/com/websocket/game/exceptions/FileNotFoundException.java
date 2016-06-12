package com.websocket.game.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "File not found!")
public class FileNotFoundException extends Exception {
    public FileNotFoundException(){super("File not found");}
}
