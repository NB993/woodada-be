package com.woodada.common.auth.adapter.out.external;

import com.woodada.common.auth.adapter.out.external.request.OAuth2AccessTokenRequest;
import com.woodada.common.auth.domain.OAuth2RegistrationProperties;
import com.woodada.common.auth.domain.OAuth2RegistrationProperties.Registration;
import com.woodada.common.auth.domain.ProviderType;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Component
public class OAuth2ApiRequestCreator {

    private final OAuth2RegistrationProperties oAuth2RegistrationProperties;

    public OAuth2ApiRequestCreator(final OAuth2RegistrationProperties oAuth2RegistrationProperties) {
        this.oAuth2RegistrationProperties = oAuth2RegistrationProperties;
    }

    public OAuth2AccessTokenRequest createAccessTokenRequest(final ProviderType providerType, final String authCode) {
        if (ObjectUtils.isEmpty(providerType)) {
            throw new IllegalArgumentException("[createAccessTokenRequest] providerType 미입력");
        }
        if (StringUtils.isEmpty(authCode)) {
            throw new IllegalArgumentException("[createAccessTokenRequest] authCode 미입력");
        }

        final Registration registration = oAuth2RegistrationProperties.getRegistration(providerType);

        return new OAuth2AccessTokenRequest(
            authCode,
            registration.clientId(),
            registration.clientSecret(),
            registration.authorizationGrantType(),
            registration.redirectUri()
        );
    }

}
