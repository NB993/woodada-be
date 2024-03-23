package com.woodada.core.diary.application.port.in;

import com.woodada.core.diary.domain.Diary;

public interface WriteDiaryUseCase {

    /**
     * 일기를 작성한다.<p><p/>
     *
     * 일기는 하루에 하나만 등록할 수 있으며, {@link WriteDiaryCommand}에 입력한 일기 작성 일시(writeDateTime)에
     * 이미 하나 이상의 일기가 등록되어 있는 경우 예외를 발생시킨다.
     * 이 경우 멤버는 해당 일시에 작성한 일기를 수정하거나, 삭제 후 재작성해야 한다.
     *
     * @param command 일기 작성 정보
     * @return 저장된 일기
     */
    Diary writeDiary(WriteDiaryCommand command);
}
