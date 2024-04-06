package com.woodada.core.diary.application.service;

import com.woodada.common.auth.argument_resolver.WddMember;
import com.woodada.common.exception.WddException;
import com.woodada.core.diary.application.port.in.DiaryModifyCommand;
import com.woodada.core.diary.application.port.in.DiaryModifyUseCase;
import com.woodada.core.diary.application.port.out.DiaryFindPort;
import com.woodada.core.diary.application.port.out.DiarySavePort;
import com.woodada.core.diary.domain.Diary;
import com.woodada.core.diary.domain.DiaryException;
import org.springframework.stereotype.Service;

@Service
public class DiaryModifyService implements DiaryModifyUseCase {

    private final DiaryFindPort diaryFindPort;
    private final DiarySavePort diarySavePort;

    public DiaryModifyService(final DiaryFindPort diaryFindPort, final DiarySavePort diarySavePort) {
        this.diaryFindPort = diaryFindPort;
        this.diarySavePort = diarySavePort;
    }

    @Override
    public void modify(final WddMember wddMember, final DiaryModifyCommand command) {
        final Diary diary = diaryFindPort.findDiary(command.id(), wddMember.getId())
            .orElseThrow(() -> new WddException(DiaryException.NONE_CONTENTS));

        diary.modify(command.title(), command.contents());
        diarySavePort.saveDiary(diary);
    }
}
