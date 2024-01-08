package com.fundy.application.exception.custom;

public class CoreApplicationException extends RuntimeException {
    protected CoreApplicationException(String message) {
        super(message);
    }
}