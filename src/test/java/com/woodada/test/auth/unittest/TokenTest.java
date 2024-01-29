package com.woodada.test.auth.unittest;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woodada.common.auth.domain.Token;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("[unittest] Token 단위테스트")
class TokenTest {

    @Test
    @DisplayName("accessToken에 null이 입력되면 NPE가 발생한다.")
    void when_access_token_null_then_then_throw_npe() {
        assertThatThrownBy(() -> new Token(null, "refreshToken"))
            .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("refreshToken에 null이 입력되면 NPE가 발생한다.")
    void when_refresh_token_null_then_then_throw_npe() {
        assertThatThrownBy(() -> new Token("accessToken", null))
            .isInstanceOf(NullPointerException.class);
    }
}
