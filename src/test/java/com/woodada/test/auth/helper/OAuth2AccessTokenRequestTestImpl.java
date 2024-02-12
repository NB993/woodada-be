package com.woodada.test.auth.helper;


import com.woodada.common.auth.application.port.out.OAuth2AccessTokenRequestPort;
import com.woodada.common.auth.domain.OAuth2AccessToken;
import com.woodada.common.auth.domain.ProviderType;

/**
 * LoginRefreshToken 테스트용 구현체
 */
//todo 테스트용 구현체 만드는 거 좋긴 한데 인터페이스 구현 메서드 따라가기 단축키 눌렀을 때 테스트 클래스도 목록에 뜨는 게 좀 불편.. 다음엔 mock 써보기
public class OAuth2AccessTokenRequestTestImpl implements OAuth2AccessTokenRequestPort {

    @Override
    public OAuth2AccessToken requestOAuth2AccessToken(ProviderType providerType, String authCode) {
        return new OAuth2AccessToken("access_token....", "Bearer");
    }
}
