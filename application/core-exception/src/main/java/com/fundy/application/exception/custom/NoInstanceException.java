package com.fundy.application.exception.custom;

public class NoInstanceException extends CoreApplicationException {
    public NoInstanceException() {
        super("해당 인스턴스가 존재하지 않음");
    }
    public NoInstanceException(String message) {
        super(message);
    }
}