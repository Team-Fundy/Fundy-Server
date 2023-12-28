package com.fundy.application.exception.custom;

public class DuplicateInstanceException extends CoreApplicationException {
    public DuplicateInstanceException() {
        super("중복된 인스턴스 존재");
    }
    public DuplicateInstanceException(String message) {
        super(message);
    }
}
