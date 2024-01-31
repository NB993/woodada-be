package com.woodada.test.auth.domain;

import com.woodada.common.auth.domain.JwtHandler;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("[unittest] JwtHandlerTest 단위테스트")
public class JwtHandlerTest {

    private JwtHandler jwtHandler = new JwtHandler();

    @DisplayName("이메일과 토큰 만료기간을 입력받으면 jwt 토큰을 생성해서 리턴한다.")
    @Test
    void when_received_email_and_expiration_period_then_create_jwt_token() {
        String token = jwtHandler.createToken("email", 0);
        Assertions.assertThat(token).isNotNull();
    }
}
