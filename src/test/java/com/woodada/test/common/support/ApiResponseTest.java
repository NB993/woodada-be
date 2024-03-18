package com.woodada.test.common.support;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woodada.common.exception.ErrorResponse;
import com.woodada.common.support.ApiResponse;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("[unittest] ApiResponse 단위테스트")
class ApiResponseTest {

    @DisplayName("리턴 데이터가 없는 성공 응답 테테스트")
    @Test
    void success_response_with_no_data() {
        final ApiResponse<Void> successWithNoData = ApiResponse.OK;

        assertThat(successWithNoData.isSuccess()).isTrue();
        assertThat(successWithNoData.getData()).isNull();
        assertThat(successWithNoData.getError()).isNull();
    }

    @DisplayName("리턴 데이터가 존재하는 성공 응답 테스트")
    @Test
    void success_response_with_data() {
        final List<Integer> data = List.of(1, 2, 3);
        final ApiResponse<List<Integer>> successWithData = ApiResponse.success(data);

        assertThat(successWithData.isSuccess()).isTrue();
        assertThat(successWithData.getData()).isEqualTo(data);
        assertThat(successWithData.getError()).isNull();
    }

    @DisplayName("success(T data)에 ErrorResponse는 입력 불가")
    @Test
    void can_not_pass_an_error_response_to_success() {
        final ErrorResponse error = ErrorResponse.badRequest(new IllegalArgumentException("예외"));

        assertThatThrownBy(() -> ApiResponse.success(error))
            .isInstanceOf(AssertionError.class)
            .hasMessage("ErrorResponse는 입력할 수 없습니다.");
    }

    @DisplayName("예외 응답 테스트")
    @Test
    void error_response() {
        final ErrorResponse error = ErrorResponse.badRequest(new IllegalArgumentException("예외"));
        final ApiResponse<Void> errorResponse = ApiResponse.error(error);

        assertThat(errorResponse.isSuccess()).isFalse();
        assertThat(errorResponse.getData()).isNull();
        assertThat(errorResponse.getError()).isEqualTo(error);
    }
}
