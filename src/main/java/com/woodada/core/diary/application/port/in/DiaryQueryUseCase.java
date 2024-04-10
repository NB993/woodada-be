package com.woodada.core.diary.application.port.in;

import com.woodada.common.auth.argument_resolver.WddMember;
import com.woodada.core.diary.domain.Diary;
import java.util.List;

public interface DiaryQueryUseCase {

    /**
     * 일기 목록을 조회한다.
     *
     * @param wddMember 인증된 멤버 정보
     * @param command   일기 조회 정보
     * @throws NullPointerException wddMember, command 중 하나라도 null이 입력된 경우
     * @return 조회된 일기 목록
     */
    List<Diary> queryDiaries(WddMember wddMember, DiaryQueryCommand command);

    /**
     * 일기를 조회한다.
     *
     * @param wddMember 인증된 멤버 정보
     * @param id        일기 id
     * @throws NullPointerException wddMember, id 중 하나라도 null이 입력된 경우
     * @return 조회된 일기
     */
    Diary queryDiary(WddMember wddMember, Long id);
}
