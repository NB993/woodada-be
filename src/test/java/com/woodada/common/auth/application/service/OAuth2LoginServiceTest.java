package com.woodada.common.auth.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.woodada.common.auth.application.port.in.OAuth2LoginUseCase;
import com.woodada.common.auth.domain.JwtHandler;
import com.woodada.common.auth.domain.ProviderType;
import com.woodada.common.auth.domain.Token;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("OAuth2LoginService 테스트")
@ExtendWith(MockitoExtension.class)
class OAuth2LoginServiceTest {

    @Mock private JwtHandler jwtHandler;

    private OAuth2LoginUseCase oAuth2LoginUseCase;

    @BeforeEach
    void setUp() {
        oAuth2LoginUseCase = new OAuth2LoginService(jwtHandler);
    }

    @Test
    void testLogin() {
        when(jwtHandler.createToken("email", Token.ACCESS_TOKEN_EXPIRATION_PERIOD))
            .thenReturn("access_token_value");
        when(jwtHandler.createToken("email", Token.REFRESH_TOKEN_EXPIRATION_PERIOD))
            .thenReturn("refresh_token_value");

        Token token = oAuth2LoginUseCase.login(ProviderType.GOOGLE, "auth_code");

        assertThat(token).isNotNull();
    }
}
