package com.woodada.common.auth.exception;

public class AuthenticationException extends RuntimeException {

    public AuthenticationException(Throwable e) {
        super(e);
    }

    public AuthenticationException(String message) {
        super(message);
    }
}
