package com.woodada.core.diary.application.port.out;

import com.woodada.core.diary.domain.Diary;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DiaryFindPort {

    /**
     * 일기 작성 요청 일자에 이미 작성된 일기의 존재 여부를 조회한다.
     *
     * @param createdBy 작성자 id
     * @param writeDate 일기 작성 일자
     * @throws NullPointerException createdBy, writeDate 중 하나라도 null이 입력된 경우
     * @return 일기 존재 여부
     */
    boolean existsDiary(Long createdBy, LocalDate writeDate);

    /**
     * 일기를 조회한다.
     *
     * @param id        일기 id
     * @param createdBy 작성자 id
     * @throws NullPointerException id, createdBy 중 하나라도 null이 입력된 경우
     * @return 일기
     */
    Optional<Diary> findDiary(Long id, Long createdBy);

    /**
     * 일기 목록을 조회한다.
     *
     * @param createdBy 작성자 id
     * @param startDate 조회 시작 일자
     * @param endDate 조회 종료 일자
     * @throws NullPointerException createdBy, startDate, endDate 중 하나라도 null이 입력된 경우
     * @return 일기 목록
     */
    List<Diary> findAll(Long createdBy, LocalDate startDate, LocalDate endDate);
}
