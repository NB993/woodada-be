package com.woodada.test.auth.web.auth;

import com.woodada.common.auth.adapter.in.OAuth2LoginController;
import com.woodada.common.auth.application.service.OAuth2LoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("OAuth2LoginController 테스트")
@ExtendWith(MockitoExtension.class)
class OAuth2LoginControllerTest {

    @Mock
    private OAuth2LoginService oAuth2LoginService;
    private OAuth2LoginController oAuth2LoginController;

    @BeforeEach
    void setUp() {
        oAuth2LoginController = new OAuth2LoginController(oAuth2LoginService);
    }

    @Test
    void callback() {
//        when(oAuth2LoginUseCase.login(ProviderType.GOOGLE, "authCode"))
//            .thenReturn(new Token("access_token", "refreshToken"));

//        final ResponseEntity<OAuth2LoginResponse> response = oAuth2LoginController.callBack("GOOGLE", "authCode");
//        final OAuth2LoginResponse body = response.getBody();
//        final HttpHeaders headers = response.getHeaders();

//        assertThat(body.accessToken()).isEqualTo("access_token");
//        assertThat(headers.containsKey(HttpHeaders.SET_COOKIE)).isTrue();
    }
}
