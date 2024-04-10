package com.woodada.core.diary.application.port.out;

import com.woodada.core.diary.domain.Diary;

public interface DiarySavePort {

    /**
     * 일기를 신규 저장한다.
     *
     * @param diary 신규 저장 될 일기
     * @throws NullPointerException diary에 null이 입력된 경우
     * @return id가 부여된 일기
     */
    Diary saveDiary(Diary diary);
}
