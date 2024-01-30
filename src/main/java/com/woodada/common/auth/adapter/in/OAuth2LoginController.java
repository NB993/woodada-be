package com.woodada.common.auth.adapter.in;

import com.woodada.common.auth.adapter.in.response.OAuth2LoginResponse;
import com.woodada.common.auth.application.port.in.OAuth2LoginUseCase;
import com.woodada.common.auth.domain.ProviderType;
import com.woodada.common.auth.domain.Token;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/oauth2/callback")
public class OAuth2LoginController {

    private final OAuth2LoginUseCase oAuth2LoginUseCase;

    public OAuth2LoginController(OAuth2LoginUseCase oAuth2LoginUseCase) {
        this.oAuth2LoginUseCase = oAuth2LoginUseCase;
    }

    @GetMapping("/{providerType}")
    public ResponseEntity<OAuth2LoginResponse> callBack(
        @PathVariable("providerType") String providerType,
        @RequestParam("code") String code
    ) {
        //todo 인증, 토큰발급 개발
        final Token token = oAuth2LoginUseCase.login(ProviderType.valueOf(providerType), code);
        final ResponseCookie refreshTokenCookie = ResponseCookie.from("refresh_token", token.refreshToken())
            .maxAge(1800)
            .httpOnly(true)
            .path("/")
            .build();

        return ResponseEntity.ok()
            .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
            .body(new OAuth2LoginResponse(token.accessToken()));
    }
}

