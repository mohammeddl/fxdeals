package com.progressoft.fxdeals.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class RequestAlreadyExistException extends RuntimeException {
    public RequestAlreadyExistException(String message) { super(message); }
}
