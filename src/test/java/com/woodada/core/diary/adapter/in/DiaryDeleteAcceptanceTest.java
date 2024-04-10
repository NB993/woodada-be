package com.woodada.core.diary.adapter.in;


import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
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
import io.restassured.http.Header;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.http.HttpHeaders;

@DisplayName("[acceptance test] 다이어리 삭제 인수 테스트")
class DiaryDeleteAcceptanceTest extends AcceptanceTestBase {

    @Autowired private JwtHandler jwtHandler;
    @Autowired private MemberRepository memberRepository;
    @Autowired private DiaryRepository diaryRepository;
    @Autowired private AuditorAware<Long> auditorAware;

    @DisplayName("인증된 멤버가 유효한 id를 입력 후 삭제를 요청하면 삭제에 성공한다.")
    @Test
    void when_member_is_authned_and_id_is_valid_then_delete_diary_success() {
        saveMember(1L);
        ((AuditorAwareImpl) auditorAware).setCurrentAuditor(1L); // todo 다이어리 CRUD 끝나고 제거..
        saveDiary("제목", "본문");

        given()
            .header(getAuthHeader(1L))
            .pathParam("id", 1L)

        .when()
            .delete("/api/v1/diaries/{id}")

        .then()
            .statusCode(200)
            .body("success", equalTo(true))
            .body("data", nullValue())
            .body("error", nullValue())
            .log().all();

        List<DiaryJpaEntity> diaries = diaryRepository.findAll();

        assertThat(diaries.size()).isEqualTo(1);
        assertThat(diaries.get(0).getId()).isEqualTo(1L);
        assertThat(diaries.get(0).getDeleted()).isEqualTo(com.woodada.support.Deleted.TRUE);
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
