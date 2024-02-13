package com.woodada.test.auth.unittest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woodada.common.auth.domain.JwtHandler;
import com.woodada.common.auth.domain.JwtProperties;
import io.jsonwebtoken.Claims;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("[unittest] JwtHandler 단위테스트")
public class JwtHandlerTest {

    private JwtHandler jwtHandler = new JwtHandler(new JwtProperties("JWT", "woodada-authn", "memberId", "K".repeat(32)));

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

    @DisplayName("decode 성공 시 memberId claim을 꺼낼 수 있다.")
    @Test
    void decode_test() {
        final String token = jwtHandler.createToken(1L, 1000, Instant.now());
        final Claims claims = jwtHandler.decode(token);
        final Long memberId = claims.get("memberId", Long.class);

        assertThat(memberId).isEqualTo(1L);
    }
}
