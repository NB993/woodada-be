package com.woodada.common.support;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.woodada.common.exception.ErrorResponse;
import lombok.Getter;

@Getter
@JsonInclude(Include.NON_NULL)
public class ApiResponse<T> {

    private final ResultCode result;
    private final T data;
    private final ErrorResponse error;

    public static final ApiResponse<Void> OK = new ApiResponse<>(ResultCode.SUCCESS, null, null);

    private ApiResponse(
        final ResultCode result,
        final T data,
        final ErrorResponse error
    ) {
        this.result = result;
        this.data = data;
        this.error = error;
    }

    public static <T> ApiResponse<T> success(final T data) {
        return new ApiResponse<>(ResultCode.SUCCESS, data, null);
    }

    public static <T> ApiResponse<T> error(final ErrorResponse error) {
        return new ApiResponse<>(ResultCode.ERROR, null, error);
    }
}
