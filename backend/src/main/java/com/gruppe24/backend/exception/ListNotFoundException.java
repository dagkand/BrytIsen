package com.gruppe24.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ListNotFoundException extends RuntimeException {
    public ListNotFoundException(String txt) {
        super(txt);
    }

    public ListNotFoundException() {
        throw new ListNotFoundException("The list was not found");
    }
}
