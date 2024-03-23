package com.woodada.core.diary.adapter.in;


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.woodada.common.auth.adapter.out.persistence.MemberJpaEntity;
import com.woodada.common.auth.adapter.out.persistence.MemberRepository;
import com.woodada.common.auth.argument_resolver.MemberHelper;
import com.woodada.common.auth.domain.Deleted;
import com.woodada.common.auth.domain.JwtHandler;
import com.woodada.common.auth.domain.Token;
import com.woodada.common.auth.domain.UserRole;
import com.woodada.core.diary.adapter.in.request.WriteDiaryRequest;
import com.woodada.core.diary.adapter.out.persistence.DiaryJpaEntity;
import com.woodada.core.diary.adapter.out.persistence.DiaryRepository;
import com.woodada.test_helper.AcceptanceTestBase;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import java.time.Instant;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;

@DisplayName("[acceptance test] 다이어리 작성 인수 테스트")
class WriteDiaryAcceptanceTest extends AcceptanceTestBase {

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
                """, WriteDiaryRequest.class))

            .when()
            .post("/api/v1/diary")

            .then()
            .statusCode(200)
            .body("success", equalTo(true))
            .body("data", nullValue())
            .body("error", nullValue())
            .log().all();

        DiaryJpaEntity savedDiary = diaryRepository.findByCreatedBy(1L).get();
        Assertions.assertThat(savedDiary.getId()).isEqualTo(1L);
        Assertions.assertThat(savedDiary.getContents()).isEqualTo("유효한 일기 본문");
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
