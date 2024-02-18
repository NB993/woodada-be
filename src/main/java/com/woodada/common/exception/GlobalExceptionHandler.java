package com.woodada.common.exception;

import com.woodada.common.auth.exception.AuthenticationException;
import com.woodada.common.support.ApiResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ApiResponse<Void> handleIllegalArgumentException(final IllegalArgumentException e) {
        return ApiResponse.error(ErrorResponse.badRequest(e));
    }

    /**
     * 지원하지 않는 HTTP 메소드 요청 예외
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ApiResponse<Void> handleHttpRequestMethodNotSupportedException(final HttpRequestMethodNotSupportedException e) {
        return ApiResponse.error(ErrorResponse.methodNotAllowed(e));
    }

    /**
     * 요청 body 파싱, 형변환 불가 예외
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ApiResponse<Void> handleHttpMessageNotReadableException(final HttpMessageNotReadableException e) {
        return ApiResponse.error(ErrorResponse.badRequest(e));
    }

    /**
     * @Valid 위반 예외
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<Void> handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        return ApiResponse.error(ErrorResponse.badRequest(e));
    }

    /**
     * DB 제약 조건 위반 예외
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ApiResponse<Void> handleConstraintViolationException(final ConstraintViolationException e) {
        return ApiResponse.error(ErrorResponse.badRequest(e));
    }

    /**
     * 인증 실패 예외
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AuthenticationException.class)
    public ApiResponse<Void> handleAuthenticationException(final AuthenticationException e) {
        log.error("[GlobalExceptionHandler][handleAuthenticationException] AuthenticationException 발생", e);
        return ApiResponse.error(ErrorResponse.authenticationError(e));
    }

    /**
     * 비즈니스 예외
     */
    @ExceptionHandler(WddException.class)
    public ResponseEntity<ApiResponse<Void>> handleWddException(final WddException e) {
        final ApiResponse response = ApiResponse.error(ErrorResponse.businessError(e));
        return ResponseEntity.status(e.getStatus())
            .body(response);
    }

    /**
     * 서버 측 에러
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ApiResponse<Void> handleException(final Exception e) {
        log.error("INTERNAL_SERVER_ERROR: ", e);
        return ApiResponse.error(ErrorResponse.internalServerError(e));
    }
}
