package com.woodada.test.common.exception;

import com.woodada.common.exception.BaseExceptionType;
import org.springframework.http.HttpStatus;

enum TestException implements BaseExceptionType {

    TEST("406", "비즈니스 예외 테스트 메시지", HttpStatus.NOT_ACCEPTABLE);

    private final String code;
    private final String message;
    private final HttpStatus status;

    TestException(
        final String code,
        final String message,
        final HttpStatus status
    ) {
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
