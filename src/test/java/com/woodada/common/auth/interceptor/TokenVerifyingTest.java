package com.woodada.common.auth.interceptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.woodada.common.auth.adapter.out.persistence.MemberRepository;
import com.woodada.common.auth.domain.Deleted;
import com.woodada.common.auth.domain.JwtHandler;
import com.woodada.common.auth.domain.JwtProperties;
import com.woodada.common.auth.domain.Token;
import com.woodada.common.auth.exception.AuthenticationException;
import com.woodada.common.auth.helper.AuthTestController;
import com.woodada.common.config.CorsProperties;
import com.woodada.common.exception.WddException;
import jakarta.servlet.http.Cookie;
import java.time.Instant;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@DisplayName("[integration test] AuthInterceptor, WddMemberResolver 토큰 검증 통합 테스트")
@ActiveProfiles("test")
@WebMvcTest(value = {AuthTestController.class, AuthInterceptor.class, JwtHandler.class, JwtProperties.class, CorsProperties.class})
class TokenVerifyingTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private JwtHandler jwtHandler;

    @MockBean private MemberRepository memberRepository;

    @DisplayName("Authorization 헤더의 토큰 scheme 이 유효하지 않으면 예외가 발생한다.")
    @Test
    void when_invalid_auth_header_then_throw_401_exception() throws Exception {
        //given
        String authHeader = "INVALID_SCHEME " + getValidToken();

        //when
        ResultActions perform = mockMvc.perform(get("/api/auth")
            .header(HttpHeaders.AUTHORIZATION, authHeader));

        //then
        perform
            .andExpect(result -> assertThat(result.getResolvedException()).isInstanceOf(WddException.class))
            .andExpect(jsonPath("result").value("ERROR"))
            .andExpect(jsonPath("error.code").value("401"))
            .andExpect(jsonPath("error.message").value("유효하지 않은 토큰"))
            .andExpect(jsonPath("error.validations").isEmpty())
            .andDo(print());
    }

    @DisplayName("토큰의 유효기간이 만료된 경우 AuthenticationException 예외가 발생한다.")
    @Test
    void when_token_expired_then_verify_refresh_token() throws Exception {
        //given
        String expiredToken = jwtHandler.createToken(1L, 0, Instant.now());
        String refreshToken = jwtHandler.createToken(1L, Token.REFRESH_TOKEN_EXPIRATION_PERIOD, Instant.now());

        Cookie refreshTokenCookie = new Cookie(Token.REFRESH_TOKEN_COOKIE_NAME, refreshToken);
        refreshTokenCookie.setHttpOnly(true);

        //when
        ResultActions perform = mockMvc.perform(get("/api/auth")
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + expiredToken)
            .cookie(refreshTokenCookie));

        //then
        perform
            .andExpect(result -> assertThat(result.getResolvedException()).isInstanceOf(AuthenticationException.class))
            .andExpect(jsonPath("result").value("ERROR"))
            .andExpect(jsonPath("error.code").value("401"))
            .andExpect(jsonPath("error.message").value("REISSUE_TOKEN"))
            .andExpect(jsonPath("error.validations").isEmpty())
            .andDo(print());
    }

    @DisplayName("토큰에서 꺼낸 memberId로 멤버 조회 실패 시 AuthenticationException 예외가 발생한다.")
    @Test
    void when_not_found_member_then_throw_exception() throws Exception {
        //given
        String notFoundMemberIdToken = jwtHandler.createToken(9999L, 100000, Instant.now());

        when(memberRepository.findByIdAndDeleted(9999L, Deleted.FALSE))
            .thenReturn(Optional.empty());

        //when
        ResultActions perform = mockMvc.perform(get("/api/auth")
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + notFoundMemberIdToken));

        //then
        perform
            .andExpect(result -> assertThat(result.getResolvedException()).isInstanceOf(AuthenticationException.class))
            .andExpect(jsonPath("result").value("ERROR"))
            .andExpect(jsonPath("error.code").value("401"))
            .andExpect(jsonPath("error.message").value("인증 실패"))
            .andExpect(jsonPath("error.validations").isEmpty())
            .andDo(print());
    }
    
    private String getValidToken() {
        return jwtHandler.createToken(1L, 10000, Instant.now());
    }

}
