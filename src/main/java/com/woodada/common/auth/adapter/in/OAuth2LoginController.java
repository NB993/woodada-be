package com.woodada.common.auth.adapter.in;

import com.woodada.common.auth.adapter.in.response.OAuth2LoginResponse;
import com.woodada.common.auth.application.port.in.OAuth2LoginUseCase;
import com.woodada.common.auth.domain.ProviderType;
import com.woodada.common.auth.domain.Token;
import com.woodada.common.support.ApiResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/login")
public class OAuth2LoginController {

    private final OAuth2LoginUseCase oAuth2LoginUseCase;

    public OAuth2LoginController(final OAuth2LoginUseCase oAuth2LoginUseCase) {
        this.oAuth2LoginUseCase = oAuth2LoginUseCase;
    }

    @GetMapping("/{providerType}")
    public ResponseEntity<ApiResponse<OAuth2LoginResponse>> login(@PathVariable("providerType") String providerType, @RequestParam("code") String code) {
        final Token token = oAuth2LoginUseCase.login(ProviderType.valueOf(providerType.toUpperCase()), code);

        final ResponseCookie refreshTokenCookie = ResponseCookie.from(Token.REFRESH_TOKEN_COOKIE_NAME, token.refreshToken())
            .maxAge(3600)
            .httpOnly(true)
            .path("/")
            .build();

        final ApiResponse<OAuth2LoginResponse> response = ApiResponse.success(new OAuth2LoginResponse(token.accessToken()));
        return ResponseEntity.ok()
            .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
            .body(response);
    }
}

