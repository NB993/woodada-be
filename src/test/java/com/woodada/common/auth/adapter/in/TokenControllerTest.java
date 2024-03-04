package com.woodada.common.auth.adapter.in;

import static org.assertj.core.api.Assertions.assertThat;

import com.woodada.common.auth.adapter.in.response.ReIssueTokenResponse;
import com.woodada.common.auth.argument_resolver.MemberHelper;
import com.woodada.common.auth.argument_resolver.WddMember;
import com.woodada.common.auth.domain.JwtHandler;
import com.woodada.common.auth.domain.UserRole;
import com.woodada.common.support.ApiResponse;
import com.woodada.test_helper.IntegrationTestBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@DisplayName("TokenController 테스트")
class TokenControllerTest extends IntegrationTestBase {

    @Autowired private ReIssueTokenController tokenController;
    @Autowired private JwtHandler jwtHandler;

    @DisplayName("인증된 사용자 정보를 전달받으면 엑세스토큰과 리프레시 토큰을 재발급한다.")
    @Test
    void testReIssueToken() {
        //given
        WddMember wddMember = MemberHelper.createWddMember(1L, "test@email.com", "테스트유저", "test_profile_url", UserRole.NORMAL);

        //when
        final ResponseEntity<ApiResponse<ReIssueTokenResponse>> responseEntity = tokenController.reIssueToken(wddMember);

        //then
        final ApiResponse<ReIssueTokenResponse> body = responseEntity.getBody();
        final ReIssueTokenResponse data = body.getData();

        Assertions.assertAll(
            () -> assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK),
            () -> assertThat(body.isSuccess()).isTrue(),
            () -> assertThat(jwtHandler.decodeToken(data.accessToken())).isEqualTo(1L),
            () -> assertThat(responseEntity.getHeaders().get(HttpHeaders.SET_COOKIE)).isNotEmpty()
        );
    }
}
