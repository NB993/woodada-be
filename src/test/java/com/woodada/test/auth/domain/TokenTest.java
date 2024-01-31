package com.woodada.test.auth.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woodada.common.auth.domain.Token;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("[unittest] Token 단위테스트")
class TokenTest {

    @Test
    @DisplayName("accessToken에 null이 입력되면 IllegalArgumentException 예외가 발생한다.")
    void when_access_token_null_then_then_throw_npe() {
        assertThatThrownBy(() -> new Token(null, "refreshToken"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("access token 이 입력되지 않았습니다.");
    }

    @Test
    @DisplayName("refreshToken에 null이 입력되면 IllegalArgumentException 예외가 발생한다.")
    void when_refresh_token_null_then_then_throw_npe() {
        assertThatThrownBy(() -> new Token("accessToken", null))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("refresh token 이 입력되지 않았습니다.");
    }

    @Test
    @DisplayName("생성된 Token은 입력받은 aceessToken과 refreshToken을 가진다.")
    void when_token_created_then_not_null() {
        Token token = new Token("AT", "RT");
        assertThat(token).isNotNull();
        assertThat(token.accessToken()).isEqualTo("AT");
        assertThat(token.refreshToken()).isEqualTo("RT");
    }
}
