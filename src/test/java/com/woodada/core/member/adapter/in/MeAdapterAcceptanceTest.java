package com.woodada.core.member.adapter.in;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woodada.common.auth.adapter.out.persistence.MemberJpaEntity;
import com.woodada.common.auth.adapter.out.persistence.MemberRepository;
import com.woodada.common.auth.argument_resolver.MemberHelper;
import com.woodada.common.auth.domain.Deleted;
import com.woodada.common.auth.domain.JwtHandler;
import com.woodada.common.auth.domain.UserRole;
import com.woodada.test_helper.AcceptanceTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import java.time.Instant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;


@DisplayName("[acceptance test] Me 어댑터 인수 테스트")
public class MeAdapterAcceptanceTest extends AcceptanceTestBase {

    @Autowired private JwtHandler jwtHandler;
    @Autowired private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        final MemberJpaEntity member = MemberHelper.createMemberJpaEntity(1L, "email", "name",
            "profile_url", UserRole.NORMAL, Deleted.FALSE);
        memberRepository.save(member);
    }

    @DisplayName("클라이언트가 유효한 토큰을 전달하면 토큰에서 memberId를 꺼내 사용자 정보를 조회하여 응답한다.")
    @Test
    void when_get_valid_token_then_return_member_info() {
        final String validAuthHeader = getAuthHeader();

        final ValidatableResponse meResponse =
            given()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, validAuthHeader)
            .when()
                .get("/api/v1/member/me")
            .then()
                .statusCode(200)
                .body("result", equalTo("SUCCESS"))
                .body("data.id", equalTo(1))
                .body("data.email", equalTo("email"))
                .body("data.name", equalTo("name"))
                .body("data.profileUrl", equalTo("profile_url"))
                .body("data.userRole", equalTo("NORMAL"))
                .log().all();
    }

    @DisplayName("2번 방식")
    @Test
    void when_get_valid_token_then_return_member_info_2() {
        //given
        final String validAuthHeader = getAuthHeader();

        //when
        final ExtractableResponse<Response> response =
            given()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, validAuthHeader)
            .when()
                .get("/api/v1/member/me")
                .then().log().all()
                .extract();

        //then
        final JsonPath result = response.jsonPath();
        assertAll(
            () -> assertThat(response.statusCode()).isEqualTo(200),
            () -> assertThat(result.getString("result")).isEqualTo("SUCCESS"),
            () -> assertThat(result.getLong("data.id")).isEqualTo(1L),
            () -> assertThat(result.getString("data.email")).isEqualTo("email"),
            () -> assertThat(result.getString("data.name")).isEqualTo("name"),
            () -> assertThat(result.getString("data.profileUrl")).isEqualTo("profile_url"),
            () -> assertThat(result.getString("data.userRole")).isEqualTo("NORMAL")
        );
    }

    private String getAuthHeader() {
        return "Bearer " + jwtHandler.createToken(1L, 10000, Instant.now());
    }
}
