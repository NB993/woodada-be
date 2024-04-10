package com.woodada.common.auth.domain;

import java.util.Map;
import java.util.Set;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.ObjectUtils;

@ConfigurationProperties(prefix = "oauth2.client")
public class OAuth2RegistrationProperties {

    private final Map<String, Registration> registration;
    private final Map<String, Provider> provider;

    public OAuth2RegistrationProperties(final Map<String, Registration> registration, final Map<String, Provider> provider) {
        this.registration = registration;
        this.provider = provider;
    }

    public Registration getRegistration(final ProviderType providerType) {
        if (ObjectUtils.isEmpty(providerType)) {
            throw new IllegalArgumentException("[getRegistration] providerType 미입력");
        }
        return registration.get(providerType.getName());
    }

    public Provider getProvider(final ProviderType providerType) {
        if (ObjectUtils.isEmpty(providerType)) {
            throw new IllegalArgumentException("[getProvider] providerType 미입력");
        }
        return provider.get(providerType.getName());
    }

    public record Registration (
        String clientId,
        String clientSecret,
        String clientAuthenticationMethod,
        String authorizationGrantType,
        String redirectUri,
        Set<String> scope
    ){

    }

    public record Provider (
        String tokenUri,
        String userInfoUri,
        String userNameAttribute
    ){

    }
}
