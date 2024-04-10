package com.woodada.common.exception;

import org.springframework.http.HttpStatus;

public interface BaseExceptionType {

    String getCode();

    String getMessage();

    HttpStatus getStatus();
}
