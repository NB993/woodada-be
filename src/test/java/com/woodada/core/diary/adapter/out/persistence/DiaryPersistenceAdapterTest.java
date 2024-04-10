package com.woodada.core.diary.adapter.out.persistence;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woodada.core.diary.domain.Diary;
import com.woodada.test_helper.IntegrationTestBase;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("[integration test] DiaryPersistenceAdapter 통합 테스트")
class DiaryPersistenceAdapterTest extends IntegrationTestBase {

    @Autowired private DiaryRepository diaryRepository;
    @Autowired private DiaryPersistenceAdapter diaryPersistenceAdapter;

    @DisplayName("[saveDiary] - 일기 도메인 엔티티가 null일 경우 NPE 발생")
    @Test
    void given_diary_is_null_when_save_diary_then_throws_npe() {
        assertThatThrownBy(() -> diaryPersistenceAdapter.saveDiary(null))
            .isInstanceOf(NullPointerException.class);
    }

    @DisplayName("[saveDiary] - 일기 도메인 엔티티가 null이 아닌 경우 일기 저장에 성공한다")
    @Test
    void given_all_args_are_not_null_when_save_diary_then_success() {
        //given
        Diary newDiary = Diary.withoutId("제목", "본문");

        //when
        Diary savedDiary = diaryPersistenceAdapter.saveDiary(newDiary);

        //then
        assertThat(savedDiary.getId()).isEqualTo(1L);
        assertThat(savedDiary.getTitle()).isEqualTo("제목");
        assertThat(savedDiary.getContents()).isEqualTo("본문");
    }

    @DisplayName("[findDiary] - id가 null일 경우 NPE 발생")
    @Test
    void given_id_is_null_when_find_diary_then_throws_npe() {
        assertThatThrownBy(() -> diaryPersistenceAdapter.findDiary(null, 1L))
            .isInstanceOf(NullPointerException.class);
    }

    @DisplayName("[findDiary] - createdBy가 null일 경우 NPE 발생")
    @Test
    void given_created_by_is_null_when_find_diary_then_throws_npe() {
        assertThatThrownBy(() -> diaryPersistenceAdapter.findDiary(1L, null))
            .isInstanceOf(NullPointerException.class);
    }

    @DisplayName("[findDiary] - 모든 인자가 null이 아닌 경우 일기 조회에 성공한다")
    @Test
    void given_all_args_are_not_null_when_find_diary_then_success() {
        //given
        Diary diary = Diary.withoutId("제목", "본문");
        DiaryJpaEntity diaryJpaEntity = DiaryJpaEntity.from(diary);
        diaryRepository.save(diaryJpaEntity);

        //when
        //todo 영속성 어댑터 테스트할 때 등록자/수정자 어떻게 처리하면 좋을까..
        Diary foundDiary = diaryPersistenceAdapter.findDiary(1L, 0L).get();

        //then
        assertThat(foundDiary.getTitle()).isEqualTo("제목");
        assertThat(foundDiary.getContents()).isEqualTo("본문");
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

    @DisplayName("[findAll] - 등록자가 null일 경우 NPE 발생")
    @Test
    void given_created_by_is_null_when_find_all_then_throws_npe() {
        assertThatThrownBy(() -> diaryPersistenceAdapter.findAll(null, LocalDate.now().minusDays(1), LocalDate.now().plusDays(1)))
            .isInstanceOf(NullPointerException.class);
    }

    @DisplayName("[findAll] - 조회 시작 일자가 null일 경우 NPE 발생")
    @Test
    void given_start_date_is_null_when_find_all_then_throws_npe() {
        assertThatThrownBy(() -> diaryPersistenceAdapter.findAll(0L, null, LocalDate.now().plusDays(1)))
            .isInstanceOf(NullPointerException.class);
    }

    @DisplayName("[findAll] - 조회 종료 일자가 null일 경우 NPE 발생")
    @Test
    void given_end_date_is_null_when_find_all_then_throws_npe() {
        assertThatThrownBy(() -> diaryPersistenceAdapter.findAll(0L, LocalDate.now().minusDays(1), null))
            .isInstanceOf(NullPointerException.class);
    }

    @DisplayName("[findAll] - 모든 인자가 null이 아닌 경우 일기 목록 조회에 성공한다.")
    @Test
    void given_all_args_are_not_null_when_find_all_then_success() {
        //given
        Diary diary = Diary.withoutId("제목", "본문");
        DiaryJpaEntity diaryJpaEntity = DiaryJpaEntity.from(diary);
        diaryRepository.save(diaryJpaEntity);

        //when
        //todo 등록자/수정자, 등록일시/수정일시가 audit에 의해 자동으로 입력되기 때문에 통제 불가능
        // => audit 관련 정보로 조회해야 하는 경우 통제가 안되는데 어떻게 테스트해야 좋을지?
        List<Diary> diaries = diaryPersistenceAdapter.findAll(0L, LocalDate.now().minusDays(1), LocalDate.now().plusDays(1));

        //then
        assertThat(diaries.size()).isEqualTo(1);
        assertThat(diaries.get(0).getId()).isEqualTo(1L);
        assertThat(diaries.get(0).getTitle()).isEqualTo("제목");
        assertThat(diaries.get(0).getContents()).isEqualTo("본문");
    }
}
