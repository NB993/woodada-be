package com.woodada.common.auth.adapter.in;

import static io.restassured.RestAssured.given;
import static io.restassured.matcher.RestAssuredMatchers.detailedCookie;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import com.woodada.common.auth.adapter.out.persistence.MemberJpaEntity;
import com.woodada.common.auth.adapter.out.persistence.MemberRepository;
import com.woodada.common.auth.argument_resolver.MemberHelper;
import com.woodada.common.auth.domain.Deleted;
import com.woodada.common.auth.domain.JwtHandler;
import com.woodada.common.auth.domain.UserRole;
import com.woodada.test_helper.AcceptanceTestBase;
import java.time.Duration;
import java.time.Instant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

@DisplayName("[acceptance test] 엑세스 토큰 재발급 인수 테스트")
class ReIssueAccessTokenAcceptanceTest extends AcceptanceTestBase {

    @Autowired private JwtHandler jwtHandler;
    @Autowired private MemberRepository memberRepository;
    @Autowired private StringRedisTemplate redisTemplate;

    @BeforeEach
    void setUp() {
        final MemberJpaEntity member = MemberHelper.createMemberJpaEntity(1L, "email", "name",
            "profile_url", UserRole.NORMAL, Deleted.FALSE);
        memberRepository.save(member);
    }

    @DisplayName("유효한 리프레시 토큰을 이용하여 엑세스 토큰과 리프레시 토큰을 함께 재발급 받을 수 있다.")
    @Test
    void testReIssueAccessToken() {
        final String validRefreshToken = jwtHandler.createToken(1L, Duration.ofMinutes(24 * 60 * 7).toSeconds(), Instant.now());
        redisTemplate.opsForSet().add("member::1::string::refresh_token", validRefreshToken);

        given()
            .cookie("refresh_token", validRefreshToken)
        .when()
            .post("/api/v1/token")
        .then()
            .statusCode(200)
            .body("success", equalTo(true))
            .body("data.accessToken",notNullValue())
            .cookie("refresh_token", notNullValue())
            .cookie("refresh_token", detailedCookie().httpOnly(true))
            .log().all();

        final String reissuedRefreshToken = redisTemplate.opsForSet().pop("member::1::string::refresh_token");
        assertThat(reissuedRefreshToken).isNotEqualTo(validRefreshToken);
    }
}
