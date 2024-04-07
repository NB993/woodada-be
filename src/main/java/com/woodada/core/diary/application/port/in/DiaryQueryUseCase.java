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
     * @return 조회된 일기 목록
     */
    List<Diary> queryDiaries(WddMember wddMember, DiaryQueryCommand command);
}
