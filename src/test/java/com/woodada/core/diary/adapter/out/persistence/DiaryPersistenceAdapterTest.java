package com.woodada.core.diary.adapter.out.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import com.woodada.core.diary.domain.Diary;
import com.woodada.test_helper.IntegrationTestBase;
import java.time.LocalDate;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("[integration test] DiaryPersistenceAdapter 통합 테스트")
class DiaryPersistenceAdapterTest extends IntegrationTestBase {

    @Autowired private DiaryRepository diaryRepository;
    @Autowired private DiaryPersistenceAdapter diaryPersistenceAdapter;

    @DisplayName("일기 작성 테스트")
    @Test
    void test_save_diary() {
        //given
        Diary newDiary = Diary.withoutId("제목", "본문");

        //when
        Diary savedDiary = diaryPersistenceAdapter.saveDiary(newDiary);

        //then
        assertThat(savedDiary.getId()).isEqualTo(1L);
        assertThat(savedDiary.getTitle()).isEqualTo("제목");
        assertThat(savedDiary.getContents()).isEqualTo("본문");
    }

    @Disabled("usecase 테스트에서 writeDate를 더 의미있게 테스트할 수 있다. 00:00를 걸쳐서 저장, 조회가 수행되면 테스트가 실패할 수 있음. + 스프링 시큐리티 적용 후 createdBy 재확인")
    @DisplayName("일기 존재 여부 조회 테스트")
    @Test
    void test_exists_diary() {
        //given
        Diary newDiary = Diary.withoutId("제목", "본문");
        diaryPersistenceAdapter.saveDiary(newDiary);

        Diary newDiaryWithSameDay = Diary.withoutId("제목", "본문");

        //when then
        assertThat(diaryPersistenceAdapter.existsDiary(0L, LocalDate.now())).isTrue();
    }
}
