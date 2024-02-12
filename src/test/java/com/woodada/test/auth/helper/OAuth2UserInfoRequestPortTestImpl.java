package com.woodada.test.auth.helper;

import com.woodada.common.auth.application.port.out.OAuth2UserInfoRequestPort;
import com.woodada.common.auth.domain.OAuth2AccessToken;
import com.woodada.common.auth.domain.OAuth2UserInfo;
import com.woodada.common.auth.domain.ProviderType;

/**
 * LoginRefreshToken 테스트용 구현체
 */
public class OAuth2UserInfoRequestPortTestImpl implements OAuth2UserInfoRequestPort {

    @Override
    public OAuth2UserInfo requestOAuth2UserInfo(ProviderType providerType, OAuth2AccessToken accessToken) {
        return new OAuth2UserInfo("test@email.com", "테스트유저", "profile_url.png");
    }
}
