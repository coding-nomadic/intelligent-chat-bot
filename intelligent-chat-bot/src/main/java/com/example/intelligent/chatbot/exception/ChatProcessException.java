package com.example.intelligent.chatbot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ChatProcessException extends RuntimeException {

    public ChatProcessException(String message, Throwable cause) {
        super(message, cause);
    }

    public ChatProcessException(String message, String errorCode) {
        super(message);
    }
}