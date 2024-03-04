package com.woodada.common.auth.adapter.in;

import static org.assertj.core.api.Assertions.assertThat;

import com.woodada.common.auth.adapter.in.response.ReIssueTokenResponse;
import com.woodada.common.auth.application.port.in.ReIssueTokenUseCase;
import com.woodada.common.auth.argument_resolver.MemberHelper;
import com.woodada.common.auth.argument_resolver.WddMember;
import com.woodada.common.auth.domain.Token;
import com.woodada.common.auth.domain.UserRole;
import com.woodada.common.support.ApiResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
@DisplayName("TokenController 테스트")
class TokenControllerTest {

    private ReIssueTokenController tokenController;

    @Mock private ReIssueTokenUseCase reIssueTokenUseCase;

    @BeforeEach
    void setUp() {
        tokenController = new ReIssueTokenController(reIssueTokenUseCase);
    }

    @Test
    void success() {
        //given
        WddMember wddMember = MemberHelper.createWddMember(1L, "test@email.com", "테스트유저", "test_profile_url", UserRole.NORMAL);
        Mockito.when(reIssueTokenUseCase.reIssue(1L))
            .thenReturn(new Token("re_issued_access_token", "re_issued_refresh_token"));

        //when
        final ResponseEntity<ApiResponse<ReIssueTokenResponse>> responseEntity = tokenController.reIssueToken(wddMember);

        //then
        final ApiResponse<ReIssueTokenResponse> body = responseEntity.getBody();
        final ReIssueTokenResponse data = body.getData();

        Assertions.assertAll(
            () -> assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK),
            () -> assertThat(body.isSuccess()).isTrue(),
            () -> assertThat(data.accessToken()).isEqualTo("re_issued_access_token"),
            () -> assertThat(responseEntity.getHeaders().get(HttpHeaders.SET_COOKIE))
                .anyMatch(cookie -> cookie.contains("re_issued_refresh_token"))
        );
    }
}
