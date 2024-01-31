package com.woodada.test.auth.web.auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.woodada.common.auth.adapter.in.OAuth2LoginController;
import com.woodada.common.auth.adapter.in.response.OAuth2LoginResponse;
import com.woodada.common.auth.application.port.in.OAuth2LoginUseCase;
import com.woodada.common.auth.domain.ProviderType;
import com.woodada.common.auth.domain.Token;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

@DisplayName("OAuth2LoginController 테스트")
@ExtendWith(MockitoExtension.class)
class OAuth2LoginControllerTest {

    @Mock
    private OAuth2LoginUseCase oAuth2LoginUseCase;
    private OAuth2LoginController oAuth2LoginController;

    @BeforeEach
    void setUp() {
        oAuth2LoginController = new OAuth2LoginController(oAuth2LoginUseCase);
    }

    @Test
    void callback() {
        when(oAuth2LoginUseCase.login(ProviderType.GOOGLE, "authCode"))
            .thenReturn(new Token("access_token", "refreshToken"));

        final ResponseEntity<OAuth2LoginResponse> response = oAuth2LoginController.callBack("GOOGLE", "authCode");
        final OAuth2LoginResponse body = response.getBody();
        final HttpHeaders headers = response.getHeaders();

        assertThat(body.accessToken()).isEqualTo("access_token");
        assertThat(headers.containsKey(HttpHeaders.SET_COOKIE)).isTrue();
    }
}
