package com.woodada.test.auth.helper;

import com.woodada.common.auth.application.port.out.OAuth2AccessTokenRequestPort;
import com.woodada.common.auth.application.port.out.OAuth2UserInfoRequestPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Configuration
public class OAuth2MockConfiguration {

    @Profile("test")
    @Bean
    @Primary
    public OAuth2AccessTokenRequestPort oAuth2AccessTokenRequestPort() {
        return new OAuth2AccessTokenRequestTestImpl();
    }

    @Profile("test")
    @Bean
    @Primary
    public OAuth2UserInfoRequestPort oAuth2UserInfoRequestPort() {
        return new OAuth2UserInfoRequestPortTestImpl();
    }
}
