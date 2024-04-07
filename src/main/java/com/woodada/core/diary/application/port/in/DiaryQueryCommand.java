package com.woodada.core.diary.application.port.in;

import java.time.LocalDate;
import org.springframework.util.ObjectUtils;

public record DiaryQueryCommand(
    LocalDate startDate,
    LocalDate endDate
) {

    public DiaryQueryCommand {
        if (ObjectUtils.isEmpty(startDate)) {
            throw new IllegalArgumentException("조회 시작 일자를 입력해 주세요.");
        }

        if (ObjectUtils.isEmpty(endDate)) {
            throw new IllegalArgumentException("조회 종료 일자를 입력해 주세요.");
        }

        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("조회 일자 범위가 유효하지 않습니다.");
        }
    }
}
