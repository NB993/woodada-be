package com.woodada.common.auth.application.port.out;

import com.woodada.common.auth.domain.OAuth2AccessToken;
import com.woodada.common.auth.domain.OAuth2UserInfo;
import com.woodada.common.auth.domain.ProviderType;

public interface OAuth2UserInfoRequestPort {

    OAuth2UserInfo requestOAuth2UserInfo(ProviderType providerType, OAuth2AccessToken accessToken);
}
