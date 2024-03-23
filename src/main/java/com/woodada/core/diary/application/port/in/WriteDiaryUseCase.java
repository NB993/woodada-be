package com.woodada.core.diary.application.port.in;

import com.woodada.common.auth.argument_resolver.WddMember;
import com.woodada.core.diary.domain.Diary;

public interface WriteDiaryUseCase {

    /**
     * 일기를 작성한다.
     * <p>
     * 일기는 하루에 하나만 등록할 수 있으며, 일기 등록 일시에 이미 하나 이상의 일기가 등록되어 있는 경우 기존 일기를 수정하거나, 삭제 후 재작성할 수 있다.
     *
     * @param wddMember 인증된 멤버 정보
     * @param command   일기 작성 정보
     * @return 저장된 일기
     * @throws com.woodada.common.exception.WddException 일기 작성 요청을 보낸 일자에 이미 등록된 일기가 존재하는 경우
     */
    Diary writeDiary(WddMember wddMember, WriteDiaryCommand command);
}
