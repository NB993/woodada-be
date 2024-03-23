package com.woodada.core.diary.application.service;

import com.woodada.common.auth.argument_resolver.WddMember;
import com.woodada.common.exception.WddException;
import com.woodada.core.diary.application.port.in.DiaryWriteCommand;
import com.woodada.core.diary.application.port.in.DiaryWriteUseCase;
import com.woodada.core.diary.application.port.out.DiaryFindPort;
import com.woodada.core.diary.application.port.out.DiarySavePort;
import com.woodada.core.diary.domain.Diary;
import com.woodada.core.diary.domain.DiaryException;
import org.springframework.stereotype.Service;

@Service
public class DiaryWriteService implements DiaryWriteUseCase {

    private final DiaryFindPort diaryFindPort;
    private final DiarySavePort diarySavePort;

    public DiaryWriteService(final DiaryFindPort diaryFindPort, final DiarySavePort diarySavePort) {
        this.diaryFindPort = diaryFindPort;
        this.diarySavePort = diarySavePort;
    }

    @Override
    public Diary writeDiary(final WddMember wddMember, final DiaryWriteCommand command) {
        boolean diaryExists = diaryFindPort.existsDiary(wddMember.getId(), command.writeDate());
        if (diaryExists) {
            throw new WddException(DiaryException.DUPLICATED_WRITE_DATE);
        }

        final Diary diary = Diary.withoutId(command.title(), command.contents());
        return diarySavePort.saveDiary(diary);
    }
}
