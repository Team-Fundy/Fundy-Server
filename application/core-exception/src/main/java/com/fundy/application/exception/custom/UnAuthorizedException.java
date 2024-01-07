package com.fundy.application.exception.custom;

public class UnAuthorizedException extends CoreApplicationException{
    public UnAuthorizedException() {
        super("올바르지 않은 로그인 / 토큰 정보");
    }

    public UnAuthorizedException(String message) {
        super(message);
    }
}
