package com.woodada.core.diary.application.port.in;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

@DisplayName("[unit test] DiaryModifyCommand 단위 테스트")
class DiaryModifyCommandTest {

    @DisplayName("일기 수정 객체의 일기 id에 null을 입력하면 예외가 발생한다.")
    @Test
    void given_null_diary_id_then_throw_exception() {
        Assertions.assertThatThrownBy(() -> new DiaryModifyCommand(null, "제목", "본문"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("수정할 일기 id를 입력해 주세요.");
    }



    @DisplayName("일기 수정 객체에 한 글자 미만의 제목을 입력 시 예외가 발생한다.")
    @ParameterizedTest
    @NullAndEmptySource
    void given_title_length_less_than_1_then_throw_exception(String title) {
        Assertions.assertThatThrownBy(() -> new DiaryModifyCommand(1L, title, "본문"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("일기 제목을 한 글자 이상 입력해 주세요.");
    }

    @DisplayName("일기 수정 객체에 한 글자 미만의 본문을 입력 시 예외가 발생한다.")
    @ParameterizedTest
    @NullAndEmptySource
    void given_contents_length_less_than_1_then_throw_exception(String contents) {
        Assertions.assertThatThrownBy(() -> new DiaryModifyCommand(1L, "제목", contents))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("일기 본문을 한 글자 이상 입력해 주세요.");
    }
}
