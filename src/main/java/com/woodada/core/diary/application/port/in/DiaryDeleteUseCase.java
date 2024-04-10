package com.woodada.core.diary.application.port.in;

import com.woodada.common.auth.argument_resolver.WddMember;

public interface DiaryDeleteUseCase {

    /**
     * 일기를 삭제한다
     *
     * @param wddMember 인증된 멤버 정보
     * @param id        일기 id
     * @throws NullPointerException wddMember, id 중 하나라도 null이 입력된 경우
     * @throws com.woodada.common.exception.WddException 일기가 조회되지 않은 경우
     *
     */
    void deleteDiary(WddMember wddMember, Long id);
}
