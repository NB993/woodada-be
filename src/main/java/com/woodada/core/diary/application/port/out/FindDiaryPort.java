package com.woodada.core.diary.application.port.out;

import java.time.LocalDate;

public interface FindDiaryPort {

    /**
     * 일기 작성 요청 일자에 이미 작성된 일기의 존재 여부를 조회한다.
     *
     * @param createdBy 작성자 id
     * @param writeDate 일기 작성 일자
     * @return 일기 존재 여부
     */
    boolean existsDiary(Long createdBy, LocalDate writeDate);
}
