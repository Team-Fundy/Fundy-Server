package com.fundy.application.exception.custom;

public class ValidationException extends CoreApplicationException {
    public ValidationException() {
        super("검증 예외 발생");
    }

    public ValidationException(String message) {
        super(message);
    }
}
