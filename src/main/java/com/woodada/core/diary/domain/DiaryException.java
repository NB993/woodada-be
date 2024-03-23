package com.woodada.core.diary.domain;

import com.woodada.common.exception.BaseExceptionType;
import org.springframework.http.HttpStatus;

public enum DiaryException implements BaseExceptionType {

    DUPLICATED_WRITE_DATE("400", "오늘 작성한 일기가 존재합니다.", HttpStatus.BAD_REQUEST);

    String code;
    String message;
    HttpStatus status;

    DiaryException(String code, String message, HttpStatus status) {
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
