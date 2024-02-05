package com.woodada.common.auth.adapter.out.external;

import com.woodada.common.auth.adapter.out.external.request.OAuth2AccessTokenRequest;
import com.woodada.common.auth.adapter.out.external.response.GoogleAccessTokenResponse;
import com.woodada.common.auth.adapter.out.external.response.GoogleUserInfoResponse;
import com.woodada.common.auth.application.port.out.OAuth2AccessTokenRequestPort;
import com.woodada.common.auth.application.port.out.OAuth2UserInfoRequestPort;
import com.woodada.common.auth.domain.OAuth2AccessToken;
import com.woodada.common.auth.domain.OAuth2UserInfo;
import com.woodada.common.auth.domain.ProviderType;
import org.springframework.stereotype.Component;

@Component
public class OAuth2Adapter implements OAuth2AccessTokenRequestPort, OAuth2UserInfoRequestPort {

    private final OAuth2ApiRequestCreator oAuth2ApiRequestCreator;
    private final GoogleAccessTokenFeignClient googleAccessTokenFeignClient;
    private final GoogleUserInfoFeignClient googleUserInfoFeignClient;

    public OAuth2Adapter(
        final OAuth2ApiRequestCreator oAuth2ApiRequestCreator,
        final GoogleAccessTokenFeignClient googleAccessTokenFeignClient,
        final GoogleUserInfoFeignClient googleUserInfoFeignClient
    ) {
        this.oAuth2ApiRequestCreator = oAuth2ApiRequestCreator;
        this.googleAccessTokenFeignClient = googleAccessTokenFeignClient;
        this.googleUserInfoFeignClient = googleUserInfoFeignClient;
    }

    @Override
    public OAuth2AccessToken requestOAuth2AccessToken(final ProviderType providerType, final String authCode) {
        final OAuth2AccessTokenRequest request = oAuth2ApiRequestCreator.createAccessTokenRequest(providerType, authCode);
        final GoogleAccessTokenResponse response = googleAccessTokenFeignClient.requestToken(
            request.code(),
            request.clientId(),
            request.clientSecret(),
            request.redirectUri(),
            request.grantType()
        );

        return response.toOAuth2AccessToken();
    }

    @Override
    public OAuth2UserInfo requestOAuth2UserInfo(final ProviderType providerType, final OAuth2AccessToken token) {
        final GoogleUserInfoResponse response = googleUserInfoFeignClient.requestUserInfo(token.accessToken());

        return response.toOAuth2UserInfo();
    }
}
