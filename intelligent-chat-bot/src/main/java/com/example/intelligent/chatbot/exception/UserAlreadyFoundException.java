package com.example.intelligent.chatbot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class UserAlreadyFoundException extends RuntimeException {

    public UserAlreadyFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserAlreadyFoundException(String message, String errorCode) {
        super(message);
    }
}