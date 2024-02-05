package com.woodada.common.auth.application.port.out;

import com.woodada.common.auth.domain.OAuth2AccessToken;
import com.woodada.common.auth.domain.ProviderType;

public interface OAuth2AccessTokenRequestPort {

    OAuth2AccessToken requestOAuth2AccessToken(ProviderType providerType, String authCode);
}
