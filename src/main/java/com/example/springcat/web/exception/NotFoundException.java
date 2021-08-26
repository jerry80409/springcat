package com.example.springcat.web.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(final String msg) {
        super(msg);
    }
}
