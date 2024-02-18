package com.woodada.common.auth.application.service;

import com.woodada.common.auth.application.port.in.OAuth2LoginUseCase;
import com.woodada.common.auth.application.port.out.MemberQueryPort;
import com.woodada.common.auth.application.port.out.MemberRegisterPort;
import com.woodada.common.auth.application.port.out.OAuth2AccessTokenRequestPort;
import com.woodada.common.auth.application.port.out.OAuth2UserInfoRequestPort;
import com.woodada.common.auth.application.port.out.RefreshTokenSavePort;
import com.woodada.common.auth.domain.JwtHandler;
import com.woodada.common.auth.domain.Member;
import com.woodada.common.auth.domain.OAuth2AccessToken;
import com.woodada.common.auth.domain.OAuth2UserInfo;
import com.woodada.common.auth.domain.ProviderType;
import com.woodada.common.auth.domain.Token;
import com.woodada.common.auth.domain.UserRole;
import jakarta.transaction.Transactional;
import java.time.Instant;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class OAuth2LoginService implements OAuth2LoginUseCase {

    private final OAuth2AccessTokenRequestPort accessTokenRequestPort;
    private final OAuth2UserInfoRequestPort userInfoRequestPort;
    private final MemberQueryPort memberQueryPort;
    private final MemberRegisterPort memberRegisterPort;
    private final RefreshTokenSavePort refreshTokenSavePort;
    private final JwtHandler jwtHandler;

    public OAuth2LoginService(
        final OAuth2AccessTokenRequestPort accessTokenRequestPort,
        final OAuth2UserInfoRequestPort userInfoRequestPort,
        final MemberQueryPort memberQueryPort,
        final MemberRegisterPort memberRegisterPort,
        final RefreshTokenSavePort refreshTokenSavePort,
        final JwtHandler jwtHandler
    ) {
        this.accessTokenRequestPort = accessTokenRequestPort;
        this.userInfoRequestPort = userInfoRequestPort;
        this.memberQueryPort = memberQueryPort;
        this.memberRegisterPort = memberRegisterPort;
        this.refreshTokenSavePort = refreshTokenSavePort;
        this.jwtHandler = jwtHandler;
    }

    @Override
    @Transactional
    public Token login(final ProviderType providerType, final String authCode) {
        final OAuth2AccessToken oAuth2AccessToken = accessTokenRequestPort.requestOAuth2AccessToken(providerType, authCode);
        final OAuth2UserInfo userInfo = userInfoRequestPort.requestOAuth2UserInfo(providerType, oAuth2AccessToken);
        final Optional<Member> optionalMember = memberQueryPort.findMember(userInfo);

        if (optionalMember.isEmpty()) {
            final Member registeredMember = register(userInfo);
            return createTokenOf(registeredMember);
        }

        final Member foundMember = optionalMember.get();
        return createTokenOf(foundMember);
    }

    private Member register(final OAuth2UserInfo userInfo) {
        final Member newMember = Member.withoutId(
            userInfo.email(),
            userInfo.name(),
            userInfo.profileUrl(),
            UserRole.NORMAL
        );
        return memberRegisterPort.register(newMember);
    }

    private Token createTokenOf(final Member member) {
        final String accessToken = jwtHandler.createToken(member.getId(), Token.ACCESS_TOKEN_EXPIRATION_PERIOD, Instant.now());
        final String refreshToken = jwtHandler.createToken(member.getId(), Token.REFRESH_TOKEN_EXPIRATION_PERIOD, Instant.now());

        refreshTokenSavePort.save(member.getId(), refreshToken);

        return new Token(accessToken, refreshToken);
    }
}
