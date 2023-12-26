package com.fundy.application.exception;

public class CoreApplicationException extends RuntimeException {
    protected CoreApplicationException(String message) {
        super(message);
    }
}