package com.simpletask.Zadatak_za_praksu.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserException extends RuntimeException {

    public UserException(String message) {
        super(message);
    }
}
