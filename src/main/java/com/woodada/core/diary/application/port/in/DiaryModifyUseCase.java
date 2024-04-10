package com.woodada.core.diary.application.port.in;

import com.woodada.common.auth.argument_resolver.WddMember;

public interface DiaryModifyUseCase {

    /**
     * 일기를 수정한다
     *
     * @param wddMember 인증된 멤버 정보
     * @param command   일기 수정 정보
     * @throws NullPointerException                      wddMember, command 중 하나라도 null이 입력된 경우
     * @throws com.woodada.common.exception.WddException 일기가 조회되지 않은 경우
     * @throws IllegalArgumentException                  입력 값이 유효 범위를 위반한 경우
     */
    void modify(WddMember wddMember, DiaryModifyCommand command);
}
