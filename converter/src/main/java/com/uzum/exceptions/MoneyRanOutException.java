package com.uzum.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class MoneyRanOutException extends RuntimeException {
    public MoneyRanOutException(String message) {
        super(message);
    }
}
