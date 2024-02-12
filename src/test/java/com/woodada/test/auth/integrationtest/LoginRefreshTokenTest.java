package com.woodada.test.auth.integrationtest;

import static org.assertj.core.api.Assertions.assertThat;

import com.woodada.common.IntegrationTestBase;
import com.woodada.common.auth.application.port.in.OAuth2LoginUseCase;
import com.woodada.common.auth.domain.ProviderType;
import com.woodada.common.auth.domain.Token;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

@DisplayName("[IntegrationTest] Refresh Token 저장 테스트")
public class LoginRefreshTokenTest extends IntegrationTestBase {

    @Autowired private StringRedisTemplate redisTemplate;
    @Autowired private OAuth2LoginUseCase oAuth2LoginUseCase;

    @Test
    @DisplayName("로그인에 성공하면 레디스에 리프레시 토큰이 저장된다.")
    void login() {
        final Token token = oAuth2LoginUseCase.login(ProviderType.GOOGLE, "auth_code....");
        final String refreshTokenSaveKey = "member::1::string::refresh_token";

        assertThat(redisTemplate.opsForSet().pop(refreshTokenSaveKey))
            .isEqualTo(token.refreshToken());
    }
}
