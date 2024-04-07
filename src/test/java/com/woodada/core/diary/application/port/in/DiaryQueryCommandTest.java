package com.woodada.core.diary.application.port.in;

import java.time.LocalDate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("[unit test] DiaryQueryCommand 단위 테스트")
public class DiaryQueryCommandTest {

    @DisplayName("일기 조회 객체의 조회 시작 일자에 null을 입력하면 예외가 발생한다.")
    @Test
    void given_null_start_date_then_throw_exception() {
        Assertions.assertThatThrownBy(() -> new DiaryQueryCommand(null, LocalDate.now()))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("조회 시작 일자를 입력해 주세요.");
    }

    @DisplayName("일기 조회 객체의 조회 종료 일자에 null을 입력하면 예외가 발생한다.")
    @Test
    void given_null_end_date_then_throw_exception() {
        Assertions.assertThatThrownBy(() -> new DiaryQueryCommand(LocalDate.now(), null))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("조회 종료 일자를 입력해 주세요.");
    }

    @DisplayName("일기 조회 객체의 조회 시작 일자가 조회 종료 일자보다 미래 일자이면 예외가 발생한다.")
    @Test
    void given_start_date_is_after_than_end_date_then_throw_exception() {
        LocalDate endDate = LocalDate.now();
        Assertions.assertThatThrownBy(() -> new DiaryQueryCommand(endDate.plusDays(1), endDate))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("조회 일자 범위가 유효하지 않습니다.");
    }
}
