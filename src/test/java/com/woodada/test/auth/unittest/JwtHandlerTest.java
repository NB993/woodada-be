package com.woodada.test.auth.unittest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woodada.common.auth.domain.JwtHandler;
import com.woodada.common.auth.domain.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("[unittest] JwtHandler 단위테스트")
public class JwtHandlerTest {

    private JwtHandler jwtHandler;

    @BeforeEach
    void setUp() {
        jwtHandler = new JwtHandler(new JwtProperties("JWT", "woodada-authn", "memberId", "K".repeat(32)));
    }

    @DisplayName("유효하지 않은 멤버 ID를 전달받으면 예외가 발생한다.")
    @MethodSource("invalidMemberId")
    @ParameterizedTest
    void when_member_id_is_null_then_throw_exception(final Long invalidMemberId) {
        assertThatThrownBy(() -> jwtHandler.createToken(invalidMemberId, 0, Instant.now()))
            .isInstanceOf(RuntimeException.class)
            .hasMessage("음");
    }
    private static Stream<Long> invalidMemberId() {
        return Stream.of(-1L, 0L, null);
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

        assertThatThrownBy(() -> jwtHandler.decode(expiredToken))
            .isInstanceOf(ExpiredJwtException.class);
    }

    @DisplayName("토큰 서명시 사용된 secret key와 decode시 사용된 secret key가 서로 다르면 예외가 발생한다.")
    @Test
    void when_trying_decode_with_invalid_secret_key_then_throw_exception() {
        final JwtHandler handlerWithSecretKeyK = new JwtHandler(new JwtProperties("JWT", "woodada-authn", "memberId", "K".repeat(32)));
        final JwtHandler handlerWithSecretKeyS = new JwtHandler(new JwtProperties("JWT", "woodada-authn", "memberId", "S".repeat(32)));

        final String tokenSignedWithK = handlerWithSecretKeyK.createToken(1L, 10000, Instant.now());

        assertThatThrownBy(() -> handlerWithSecretKeyS.decode(tokenSignedWithK))
            .isInstanceOf(SignatureException.class);
    }

    @DisplayName("decode 성공 시 memberId claim을 꺼낼 수 있다.")
    @Test
    void decode_test() {
        final String token = createTokenString();
        final Claims claims = jwtHandler.decode(token);
        final Long memberId = claims.get("memberId", Long.class);

        assertThat(memberId).isEqualTo(1L);
    }

    @DisplayName("token String을 전달하여 바로 memberId를 리턴받을 수 있다.")
    @Test
    void extract_member_id_test() {
        final String token = createTokenString();
        final Long memberId = jwtHandler.extractMemberId(token);

        assertThat(memberId).isEqualTo(1L);
    }

    private String createTokenString() {
        return jwtHandler.createToken(1L, 1000, Instant.now());
    }
}
