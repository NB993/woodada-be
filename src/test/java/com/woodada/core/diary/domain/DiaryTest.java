package com.woodada.core.diary.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woodada.support.Deleted;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

@DisplayName("[unit test] Diary 단위 테스트")
public class DiaryTest {

    @DisplayName("일기 수정 시 제목을 한 글자 미만으로 ₩입력하면 예외가 발생한다.")
    @ParameterizedTest
    @NullAndEmptySource
    void given_title_length_less_than_1_when_modify_then_throw_exception(String title) {
        Diary diary = Diary.withId(1L, "수정 전 제목", "수정 전 본문", Deleted.FALSE, 1L, LocalDateTime.now(), 1L, LocalDateTime.now());

        assertThatThrownBy(() -> diary.modify(title, "수정 후 본문"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("수정할 제목을 한 글자 이상 입력해 주세요.");
    }

    @DisplayName("일기 수정 시 본문을 한 글자 미만으로 ₩입력하면 예외가 발생한다.")
    @ParameterizedTest
    @NullAndEmptySource
    void given_contents_length_less_than_1_when_modify_then_throw_exception(String contents) {
        Diary diary = Diary.withId(1L, "수정 전 제목", "수정 전 본문", Deleted.FALSE, 1L, LocalDateTime.now(), 1L, LocalDateTime.now());

        assertThatThrownBy(() -> diary.modify("수정 후 제목", contents))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("수정할 본문을 한 글자 이상 입력해 주세요.");
    }

    @DisplayName("한 글자 이상의 제목과 본문을 전달하면 일기를 수정할 수 있다.")
    @Test
    void given_title_and_contents_greater_than_or_equal_to_1_when_modify_then_success() {
        Diary diary = Diary.withId(1L, "수정 전 제목", "수정 전 본문", Deleted.FALSE, 1L, LocalDateTime.now(), 1L, LocalDateTime.now());

        diary.modify("제목", "본문");

        assertThat(diary.getTitle()).isEqualTo("제목");
        assertThat(diary.getContents()).isEqualTo("본문");
    }

    @DisplayName("삭제를 요청하면 삭제 여부 필드가 TRUE가 된다.")
    @Test
    void when_delete_then_deleted_changed_to_true() {
        Diary diary = Diary.withId(1L, "제목", "본문", Deleted.FALSE, 1L, LocalDateTime.now(), 1L, LocalDateTime.now());
        diary.delete();

        assertThat(diary.getDeleted()).isEqualTo(Deleted.TRUE);
    }
}
