package com.woodada.common.auth.application.service;

import com.woodada.common.auth.application.port.in.OAuth2LoginUseCase;
import com.woodada.common.auth.domain.ProviderType;
import com.woodada.common.auth.domain.Token;
import org.springframework.stereotype.Service;

@Service
public class OAuth2LoginService implements OAuth2LoginUseCase {

    @Override
    public Token login(final ProviderType providerType, final String code) {

        return null;
    }
}
