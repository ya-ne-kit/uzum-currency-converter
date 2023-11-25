package com.uzum.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class SecretKeyNotEqualsException extends RuntimeException {
    public SecretKeyNotEqualsException(String message) {
        super(message);
    }
}
