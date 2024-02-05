package com.woodada.common.auth.application.service;

import com.woodada.common.auth.application.port.in.OAuth2LoginUseCase;
import com.woodada.common.auth.application.port.out.OAuth2AccessTokenRequestPort;
import com.woodada.common.auth.domain.JwtHandler;
import com.woodada.common.auth.domain.OAuth2AccessToken;
import com.woodada.common.auth.domain.ProviderType;
import com.woodada.common.auth.domain.Token;
import java.time.Instant;
import org.springframework.stereotype.Component;

@Component
public class OAuth2LoginService implements OAuth2LoginUseCase {

    private final OAuth2AccessTokenRequestPort accessTokenRequestPort;
    private final JwtHandler jwtHandler;

    public OAuth2LoginService(final OAuth2AccessTokenRequestPort accessTokenRequestPort, final JwtHandler jwtHandler) {
        this.accessTokenRequestPort = accessTokenRequestPort;
        this.jwtHandler = jwtHandler;
    }

    public Token login(final ProviderType providerType, final String authCode) {
        final OAuth2AccessToken oAuth2AccessToken = accessTokenRequestPort.requestOAuth2AccessToken(providerType, authCode);
        return new Token(
            jwtHandler.createToken("email", Token.ACCESS_TOKEN_EXPIRATION_PERIOD, Instant.now()),
            jwtHandler.createToken("email", Token.REFRESH_TOKEN_EXPIRATION_PERIOD, Instant.now())
        );
    }
}
