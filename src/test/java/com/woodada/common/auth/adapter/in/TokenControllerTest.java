package com.woodada.common.auth.adapter.in;

import static org.assertj.core.api.Assertions.assertThat;

import com.woodada.common.auth.adapter.in.response.ReIssueTokenResponse;
import com.woodada.common.support.ApiResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@DisplayName("TokenController 테스트")
class TokenControllerTest {

    private ReIssueTokenController tokenController;

    @BeforeEach
    void setUp() {
        tokenController = new ReIssueTokenController();
    }

    @Test
    void success() {
        //when
        final ResponseEntity<ApiResponse<ReIssueTokenResponse>> responseEntity = tokenController.reIssueToken();

        //then
        final ApiResponse<ReIssueTokenResponse> body = responseEntity.getBody();
        final ReIssueTokenResponse data = body.getData();

        Assertions.assertAll(
            () -> assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK),
            () -> assertThat(body.isSuccess()).isTrue(),
            () -> assertThat(data.accessToken()).isNotNull()
        );
    }
}
