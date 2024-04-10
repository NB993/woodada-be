package com.woodada.core.diary.application.service;

import com.woodada.common.auth.argument_resolver.WddMember;
import com.woodada.common.exception.WddException;
import com.woodada.core.diary.application.port.in.DiaryDeleteUseCase;
import com.woodada.core.diary.application.port.out.DiaryFindPort;
import com.woodada.core.diary.application.port.out.DiarySavePort;
import com.woodada.core.diary.domain.Diary;
import com.woodada.core.diary.domain.DiaryException;
import java.util.Objects;
import org.springframework.stereotype.Service;

@Service
public class DiaryDeleteService implements DiaryDeleteUseCase {

    private final DiaryFindPort diaryFindPort;
    private final DiarySavePort diarySavePort;

    public DiaryDeleteService(final DiaryFindPort diaryFindPort, final DiarySavePort diarySavePort) {
        this.diaryFindPort = diaryFindPort;
        this.diarySavePort = diarySavePort;
    }

    @Override
    public void deleteDiary(final WddMember wddMember, final Long id) {
        Objects.requireNonNull(wddMember);
        Objects.requireNonNull(id);

        final Diary diary = diaryFindPort.findDiary(id, wddMember.getId())
            .orElseThrow(() -> new WddException(DiaryException.NONE_CONTENTS));
        diary.delete();
        diarySavePort.saveDiary(diary);
    }
}
