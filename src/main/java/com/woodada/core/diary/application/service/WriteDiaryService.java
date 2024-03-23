package com.woodada.core.diary.application.service;

import com.woodada.common.auth.argument_resolver.WddMember;
import com.woodada.common.exception.WddException;
import com.woodada.core.diary.application.port.in.WriteDiaryCommand;
import com.woodada.core.diary.application.port.in.WriteDiaryUseCase;
import com.woodada.core.diary.application.port.out.FindDiaryPort;
import com.woodada.core.diary.application.port.out.SaveDiaryPort;
import com.woodada.core.diary.domain.Diary;
import com.woodada.core.diary.domain.DiaryException;
import org.springframework.stereotype.Service;

@Service
public class WriteDiaryService implements WriteDiaryUseCase {

    private final FindDiaryPort findDiaryPort;
    private final SaveDiaryPort saveDiaryPort;

    public WriteDiaryService(final FindDiaryPort findDiaryPort, final SaveDiaryPort saveDiaryPort) {
        this.findDiaryPort = findDiaryPort;
        this.saveDiaryPort = saveDiaryPort;
    }

    @Override
    public Diary writeDiary(final WddMember wddMember, final WriteDiaryCommand command) {
        boolean diaryExists = findDiaryPort.existsDiary(wddMember.getId(), command.writeDate());
        if (diaryExists) {
            throw new WddException(DiaryException.DUPLICATED_WRITE_DATE);
        }

        final Diary diary = Diary.withoutId(command.title(), command.contents());
        return saveDiaryPort.saveDiary(diary);
    }
}
