package com.fundy.application.exception;

public class CoreExceptionFactory {
    public static CoreApplicationException createBasic(CoreExceptionType exceptionType) {
        switch (exceptionType) {
            case DUPLICATE:
                return new DuplicateInstanceException("중복 존재");
            default:
                return new CoreApplicationException("Core Exception");
        }
    }

    public static CoreApplicationException createWithMessage(CoreExceptionType exceptionType, String message) {
        switch (exceptionType) {
            case DUPLICATE:
                return new DuplicateInstanceException(message);
            default:
                return new CoreApplicationException(message);
        }
    }
}