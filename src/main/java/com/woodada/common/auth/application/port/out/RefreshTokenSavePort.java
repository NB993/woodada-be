package com.woodada.common.auth.application.port.out;

public interface RefreshTokenSavePort {

    void save(Long memberId, String refreshToken);
}
