package com.woodada.common.auth.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("[unittest] JwtHandler 단위테스트")
class JwtHandlerTest {

    private JwtHandler jwtHandler;

    @BeforeEach
    void setUp() {
        jwtHandler = new JwtHandler(new JwtProperties(
            "JWT", "woodada-authn", "memberId", "K".repeat(32), "Bearer "));
    }

    @DisplayName("멤버 ID에 null을 입력하면 NPE를 던진다.")
    @Test
    void when_member_id_is_null_then_throw_exception() {
        assertThatThrownBy(() -> jwtHandler.createToken(null, Token.ACCESS_TOKEN_EXPIRATION_PERIOD, Instant.now()))
            .isInstanceOf(NullPointerException.class);
    }

    @DisplayName("멤버 ID와 토큰 만료기간을 입력받으면 jwt 토큰을 생성해서 리턴한다.")
    @Test
    void when_received_email_and_expiration_period_then_create_jwt_token() {
        String token = jwtHandler.createToken(1L, 0, Instant.now());

        assertThat(token).isNotNull();
    }

    @DisplayName("멤버 ID가 다르면 토큰 값도 다르다.")
    @Test
    void when_received_different_email_then_create_different_jwt_token() {
        String tokenOne = jwtHandler.createToken(1L, 0, Instant.now());
        String tokenTwo = jwtHandler.createToken(2L, 0, Instant.now());

        assertThat(tokenOne).isNotEqualTo(tokenTwo);
    }

    @DisplayName("만료 기간이 다르면 토큰 값도 다르다.")
    @Test
    void when_received_different_expiration_then_return_different_token() {
        String tokenOne = jwtHandler.createToken(1L, 0, Instant.now());
        String tokenTwo = jwtHandler.createToken(1L, 9999, Instant.now());

        assertThat(tokenOne).isNotEqualTo(tokenTwo);
    }

    @DisplayName("발행 일시가 다르면 토큰 값도 다르다.")
    @Test
    void when_received_different_issue_date_time_then_return_different_token() {
        Instant issuedAtOne = LocalDateTime.of(2024, 2, 1, 0, 0).toInstant(ZoneOffset.UTC);
        Instant issuedAtTwo = LocalDateTime.of(9999, 2, 1, 0, 0).toInstant(ZoneOffset.UTC);
        String tokenOne = jwtHandler.createToken(1L, 0, issuedAtOne);
        String tokenTwo = jwtHandler.createToken(1L, 0, issuedAtTwo);

        assertThat(tokenOne).isNotEqualTo(tokenTwo);
    }

    @DisplayName("기간이 만료된 토큰을 decode시 예외가 발생한다.")
    @Test
    void when_token_expired_then_throw_exception() {
        final String expiredToken = jwtHandler.createToken(1L, 0, Instant.now());
        final String authHeader = "Bearer " + expiredToken;

        assertThatThrownBy(() -> jwtHandler.decode(authHeader))
            .isInstanceOf(ExpiredJwtException.class);
    }

    @DisplayName("토큰 서명시 사용된 secret key와 decode시 사용된 secret key가 서로 다르면 예외가 발생한다.")
    @Test
    void when_trying_decode_with_invalid_secret_key_then_throw_exception() {
        final JwtHandler handlerWithSecretKeyK = new JwtHandler(new JwtProperties("JWT", "woodada-authn", "memberId", "K".repeat(32), "Bearer "));
        final JwtHandler handlerWithSecretKeyS = new JwtHandler(new JwtProperties("JWT", "woodada-authn", "memberId", "S".repeat(32), "Bearer "));

        final String tokenSignedWithK = handlerWithSecretKeyK.createToken(1L, 10000, Instant.now());
        final String authHeader = "Bearer " + tokenSignedWithK;

        assertThatThrownBy(() -> handlerWithSecretKeyS.decode(authHeader))
            .isInstanceOf(SignatureException.class);
    }

    @DisplayName("decode 성공 시 memberId claim을 꺼낼 수 있다.")
    @Test
    void decode_test() {
        final String token = createTokenString();
        final String authHeader = "Bearer " + token;
        final Claims claims = jwtHandler.decode(authHeader);
        final Long memberId = claims.get("memberId", Long.class);

        assertThat(memberId).isEqualTo(1L);
    }

    @DisplayName("token String을 전달하여 바로 memberId를 리턴받을 수 있다.")
    @Test
    void extract_member_id_test() {
        final String token = createTokenString();
        final String authHeader = "Bearer " + token;
        final Long memberId = jwtHandler.decodeTokenWithHeader(authHeader);

        assertThat(memberId).isEqualTo(1L);
    }

    private String createTokenString() {
        return jwtHandler.createToken(1L, 1000, Instant.now());
    }
}
