package com.woodada.common.auth.exception;

import com.woodada.common.exception.BaseExceptionType;
import org.springframework.http.HttpStatus;

public enum AuthException implements BaseExceptionType {

    INVALID_TOKEN_TYPE("401", "사용자 인증 실패.", HttpStatus.UNAUTHORIZED);

    private final String code;
    private final String message;
    private final HttpStatus status;

    AuthException(final String code, final String message, final HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }


    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public HttpStatus getStatus() {
        return this.status;
    }
}
