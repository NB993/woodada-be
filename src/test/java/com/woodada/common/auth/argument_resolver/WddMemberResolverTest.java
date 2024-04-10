package com.woodada.common.auth.argument_resolver;

import static io.restassured.RestAssured.given;
import static org.mockito.Mockito.when;

import com.woodada.common.auth.adapter.out.persistence.MemberJpaEntity;
import com.woodada.common.auth.adapter.out.persistence.MemberRepository;
import com.woodada.common.auth.domain.Deleted;
import com.woodada.common.auth.domain.JwtHandler;
import com.woodada.common.auth.domain.UserRole;
import com.woodada.test_helper.AcceptanceTestBase;
import java.time.Instant;
import java.util.Optional;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

@DisplayName("[integration test] WddMemberResolver 통합 테스트")
class WddMemberResolverTest extends AcceptanceTestBase {

    @Autowired private JwtHandler jwtHandler;
    @MockBean private MemberRepository memberRepository;

    @DisplayName("Authorization 헤더의 토큰 scheme 이 유효하지 않으면 예외가 발생한다.")
    @Test
    void when_invalid_auth_header_then_throw_401_exception() {
        //given
        String authHeader = "INVALID_SCHEME " + getValidToken();

        //when then
        given()
            .header(HttpHeaders.AUTHORIZATION, authHeader)
        .when()
            .get("/api/auth")
        .then()
            .statusCode(HttpStatus.UNAUTHORIZED.value())
            .body("success", Matchers.equalTo(false))
            .body("error.code", Matchers.equalTo("401"))
            .body("error.message", Matchers.equalTo("인증 실패"))
            .body("error.validations", Matchers.empty())
            .log().all();
    }

    @DisplayName("토큰의 유효기간이 만료된 경우 AuthenticationException 예외가 발생한다.")
    @Test
    void when_token_expired_then_verify_refresh_token() {
        //given
        String expiredToken = jwtHandler.createToken(1L, 0, Instant.now());

        //when then
        given()
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + expiredToken)
        .when()
            .get("/api/auth")
        .then()
            .statusCode(HttpStatus.UNAUTHORIZED.value())
            .body("success", Matchers.equalTo(false))
            .body("error.code", Matchers.equalTo("401"))
            .body("error.message", Matchers.equalTo("REISSUE_TOKEN"))
            .body("error.validations", Matchers.empty())
            .log().all();
    }

    @DisplayName("토큰에서 꺼낸 memberId로 멤버 조회 실패 시 AuthenticationException 예외가 발생한다.")
    @Test
    void when_not_found_member_then_throw_exception() {
        //given
        String notFoundMemberIdToken = jwtHandler.createToken(9999L, 100000, Instant.now());

        when(memberRepository.findByIdAndDeleted(9999L, Deleted.FALSE))
            .thenReturn(Optional.empty());

        //when then
        given()
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + notFoundMemberIdToken)
        .when()
            .get("/api/auth")
        .then()
            .statusCode(HttpStatus.UNAUTHORIZED.value())
            .body("success", Matchers.equalTo(false))
            .body("error.code", Matchers.equalTo("401"))
            .body("error.message", Matchers.equalTo("인증 실패"))
            .body("error.validations", Matchers.empty())
            .log().all();
    }

    @DisplayName("유효한 토큰을 전달받으면 인증을 마치고 요청이 핸들러로 전달된다.")
    @Test
    void when_get_valid_token_then_ok() {
        //given
        String validToken = getValidToken();

        MemberJpaEntity member = getMemberJpaEntity(1L);
        when(memberRepository.findByIdAndDeleted(1L, Deleted.FALSE))
            .thenReturn(Optional.of(member));

        //when then
        given()
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + validToken)
        .when()
            .get("/api/auth")
        .then()
            .statusCode(HttpStatus.OK.value())
            .log().all();
    }

    private String getValidToken() {
        return jwtHandler.createToken(1L, 10000, Instant.now());
    }

    private MemberJpaEntity getMemberJpaEntity(final Long memberId) {
        return MemberHelper.createMemberJpaEntity(memberId, "test@email.com", "테스트유저", "/profile.png", UserRole.NORMAL, Deleted.FALSE);
    }
}
