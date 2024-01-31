package com.woodada.test.auth.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.woodada.common.auth.domain.JwtHandler;
import java.time.Instant;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("[unittest] JwtHandlerTest 단위테스트")
public class JwtHandlerTest {

    private JwtHandler jwtHandler = new JwtHandler();

    @DisplayName("이메일과 토큰 만료기간을 입력받으면 jwt 토큰을 생성해서 리턴한다.")
    @Test
    void when_received_email_and_expiration_period_then_create_jwt_token() {
        String token = jwtHandler.createToken("email", 0, Instant.now());

        assertThat(token).isNotNull();
    }

    @DisplayName("이메일이 다르면 토큰 값도 다르다.")
    @Test
    void when_received_different_email_then_create_different_jwt_token() {
        String tokenOne = jwtHandler.createToken("email_one", 0, Instant.now());
        String tokenTwo = jwtHandler.createToken("email_two", 0, Instant.now());

        assertThat(tokenOne).isNotEqualTo(tokenTwo);
    }
}
