package com.woodada.core.diary.adapter.in;


import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.woodada.common.auth.adapter.out.persistence.MemberJpaEntity;
import com.woodada.common.auth.adapter.out.persistence.MemberRepository;
import com.woodada.common.auth.argument_resolver.MemberHelper;
import com.woodada.common.auth.domain.Deleted;
import com.woodada.common.auth.domain.JwtHandler;
import com.woodada.common.auth.domain.Token;
import com.woodada.common.auth.domain.UserRole;
import com.woodada.core.diary.adapter.in.request.DiaryWriteRequest;
import com.woodada.core.diary.adapter.out.persistence.DiaryJpaEntity;
import com.woodada.core.diary.adapter.out.persistence.DiaryRepository;
import com.woodada.test_helper.AcceptanceTestBase;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;

@DisplayName("[acceptance test] 다이어리 작성 인수 테스트")
class DiaryWriteAcceptanceTest extends AcceptanceTestBase {

    @Autowired private JwtHandler jwtHandler;
    @Autowired private MemberRepository memberRepository;
    @Autowired private DiaryRepository diaryRepository;

    @DisplayName("인증된 멤버가 유효한 일기 제목, 본문을 입력 후 저장을 요청하면 저장에 성공한다.")
    @Test
    void when_valid_content_then_diary_saved() throws JsonProcessingException {
        saveMember(1L);

        given()
            .contentType(ContentType.JSON)
            .header(getAuthHeader(1L))
            .body(objectMapper.readValue("""
                {
                    "title": "유효한 일기 제목",
                    "contents": "유효한 일기 본문"
                }
                """, DiaryWriteRequest.class))

            .when()
            .post("/api/v1/diary")

            .then()
            .statusCode(200)
            .body("success", equalTo(true))
            .body("data", nullValue())
            .body("error", nullValue())
            .log().all();

        List<DiaryJpaEntity> diaries = diaryRepository.findAll();

        assertThat(diaries.size()).isGreaterThan(0);
        assertThat(diaries.get(0).getTitle()).isEqualTo("유효한 일기 제목");
        assertThat(diaries.get(0).getContents()).isEqualTo("유효한 일기 본문");
    }

    @DisplayName("일기 본문이 5000자를 초과하면 저장에 실패한다.")
    @Test
    void when_contents_size_over_5000_then_throw_exception() throws JsonProcessingException {
        saveMember(1L);

        String titleOver100 = String.format("""
                {
                    "title": "일기 제목",
                    "contents": "%s"
                }
                """, "T".repeat(5001));

        given()
            .contentType(ContentType.JSON)
            .header(getAuthHeader(1L))
            .body(objectMapper.readValue(titleOver100, DiaryWriteRequest.class))

            .when()
            .post("/api/v1/diary")

            .then()
            .statusCode(400)
            .body("success", equalTo(false))
            .body("data", nullValue())
            .body("error.code", equalTo("400"))
            .body("error.message", equalTo("입력 조건을 위반하였습니다."))
            .body("error.validations.size()", equalTo(1))
            .body("error.validations.field", everyItem(is("contents")))
            .body("error.validations.message", everyItem(is("크기가 1에서 5000 사이여야 합니다")))
            .body("error.validations.value", everyItem(is("T".repeat(5001))))
            .log().all();
    }

    @DisplayName("일기 제목이 100자를 초과하면 저장에 실패한다.")
    @Test
    void when_title_size_over_100_then_throw_exception() throws JsonProcessingException {
        saveMember(1L);

        String titleOver100 = String.format("""
                {
                    "title": "%s",
                    "contents": "일기 본문"
                }
                """, "T".repeat(101));

        given()
            .contentType(ContentType.JSON)
            .header(getAuthHeader(1L))
            .body(objectMapper.readValue(titleOver100, DiaryWriteRequest.class))

            .when()
            .post("/api/v1/diary")

            .then()
            .statusCode(400)
            .body("success", equalTo(false))
            .body("data", nullValue())
            .body("error.code", equalTo("400"))
            .body("error.message", equalTo("입력 조건을 위반하였습니다."))
            .body("error.validations.size()", equalTo(1))
            .body("error.validations.field", everyItem(is("title")))
            .body("error.validations.message", everyItem(is("크기가 1에서 100 사이여야 합니다")))
            .body("error.validations.value", everyItem(is("T".repeat(101))))
            .log().all();
    }

    private Header getAuthHeader(Long memberId) {
        final String token = "Bearer " + jwtHandler.createToken(memberId, Token.ACCESS_TOKEN_EXPIRATION_PERIOD, Instant.now());
        return new Header(HttpHeaders.AUTHORIZATION, token);
    }

    private void saveMember(Long memberId) {
        MemberJpaEntity memberJpaEntity = MemberHelper.createMemberJpaEntity(memberId, "test@email.com", "테스트유저", "test_profile_url",
            UserRole.NORMAL, Deleted.FALSE);
        memberRepository.save(memberJpaEntity);
    }
}
