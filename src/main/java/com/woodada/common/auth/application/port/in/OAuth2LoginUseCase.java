package com.woodada.common.auth.application.port.in;

import com.woodada.common.auth.domain.ProviderType;
import com.woodada.common.auth.domain.Token;

public interface OAuth2LoginUseCase {

    Token login(ProviderType providerType, String code);
}
