package com.woodada.core.diary.adapter.in;


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

import com.woodada.common.auth.adapter.out.persistence.MemberJpaEntity;
import com.woodada.common.auth.adapter.out.persistence.MemberRepository;
import com.woodada.common.auth.argument_resolver.MemberHelper;
import com.woodada.common.auth.domain.Deleted;
import com.woodada.common.auth.domain.JwtHandler;
import com.woodada.common.auth.domain.Token;
import com.woodada.common.auth.domain.UserRole;
import com.woodada.common.config.jpa.AuditorAwareImpl;
import com.woodada.core.diary.adapter.out.persistence.DiaryJpaEntity;
import com.woodada.core.diary.adapter.out.persistence.DiaryRepository;
import com.woodada.core.diary.domain.Diary;
import com.woodada.test_helper.AcceptanceTestBase;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.http.HttpHeaders;

@DisplayName("[acceptance test] 다이어리 조회 인수 테스트")
class DiaryQueryAcceptanceTest extends AcceptanceTestBase {

    @Autowired private JwtHandler jwtHandler;
    @Autowired private MemberRepository memberRepository;
    @Autowired private DiaryRepository diaryRepository;
    @Autowired private AuditorAware<Long> auditorAware;

    @DisplayName("인증된 멤버가 유효한 조회 시작 일자, 조회 종료 일자를 입력 후 조회를 요청하면 일기 목록 조회에 성공한다.")
    @Test
    void when_member_is_authned_and_all_parameters_are_valid_then_query_diaries_success() {
        //given
        saveMember(1L);
        ((AuditorAwareImpl) auditorAware).setCurrentAuditor(1L); // todo 다이어리 CRUD 끝나고 제거..
        saveDiary("제목", "본문");

        LocalDate nowDate = LocalDate.now().minusDays(1);

        given()
            .contentType(ContentType.JSON)
            .header(getAuthHeader(1L))
            .queryParam("startDate", nowDate.minusDays(1).toString())
            .queryParam("endDate", nowDate.plusDays(1).toString())

        .when()
            .get("/api/v1/diaries")

        .then()
            .statusCode(200)
            .body("success", equalTo(true))
            .body("data.id", everyItem(is(1)))
            .body("data.title", everyItem(is("제목")))
            .body("data.contents", everyItem(is("본문")))
            .body("data.createdAt", everyItem(notNullValue(LocalDateTime.class)))
            .body("data.modifiedAt", everyItem(notNullValue(LocalDateTime.class)))
            .body("error", nullValue())
            .log().all();
    }

    @DisplayName("인증된 멤버가 유효한 일기 id를 입력 후 조회를 요청하면 일기 단건 조회에 성공한다.")
    @Test
    void when_member_is_authned_and_id_is_valid_then_query_diary_success() {
        //given
        saveMember(1L);
        ((AuditorAwareImpl) auditorAware).setCurrentAuditor(1L); // todo 다이어리 CRUD 끝나고 제거..
        saveDiary("제목", "본문");

        given()
            .contentType(ContentType.JSON)
            .header(getAuthHeader(1L))
            .pathParam("id", 1L)

        .when()
            .get("/api/v1/diaries/{id}")

        .then()
            .statusCode(200)
            .body("success", equalTo(true))
            .body("data.id", equalTo(1))
            .body("data.title", equalTo("제목"))
            .body("data.contents", equalTo("본문"))
            .body("data.createdAt", notNullValue(LocalDateTime.class))
            .body("data.modifiedAt", notNullValue(LocalDateTime.class))
            .body("error", nullValue())
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

    private void saveDiary(String title, String contents) {
        Diary diary = Diary.withoutId(title, contents);
        DiaryJpaEntity diaryJpaEntity = DiaryJpaEntity.from(diary);
        diaryRepository.save(diaryJpaEntity);
    }
}
