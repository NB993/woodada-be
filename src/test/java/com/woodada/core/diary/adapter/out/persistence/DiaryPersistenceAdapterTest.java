package com.woodada.core.diary.adapter.out.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import com.woodada.core.diary.domain.Diary;
import com.woodada.test_helper.IntegrationTestBase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("[integration test] DiaryPersistenceAdapter 통합 테스트")
class DiaryPersistenceAdapterTest extends IntegrationTestBase {

    @Autowired
    private DiaryPersistenceAdapter diaryPersistenceAdapter;

    @DisplayName("id가 없는 일기 도메인 엔티티를 전달하면 저장 후 id와 함께 리턴한다.")
    @Test
    void given_diary_domain_entity_without_id_then_save_and_return_diary_with_id() {
        //given
        Diary newDiary = Diary.withoutId("제목", "본문");

        //when
        Diary savedDiary = diaryPersistenceAdapter.saveDiary(newDiary);

        //then
        assertThat(savedDiary.getId()).isEqualTo(1L);
        assertThat(savedDiary.getTitle()).isEqualTo("제목");
        assertThat(savedDiary.getContents()).isEqualTo("본문");
    }

}
