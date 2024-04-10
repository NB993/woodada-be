package com.woodada.core.diary.application.service;

import com.woodada.common.auth.argument_resolver.WddMember;
import com.woodada.common.exception.WddException;
import com.woodada.core.diary.application.port.in.DiaryQueryCommand;
import com.woodada.core.diary.application.port.in.DiaryQueryUseCase;
import com.woodada.core.diary.application.port.out.DiaryFindPort;
import com.woodada.core.diary.domain.Diary;
import com.woodada.core.diary.domain.DiaryException;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Service;

@Service
public class DiaryQueryService implements DiaryQueryUseCase {

    private final DiaryFindPort diaryFindPort;

    public DiaryQueryService(final DiaryFindPort diaryFindPort) {
        this.diaryFindPort = diaryFindPort;
    }

    @Override
    public List<Diary> queryDiaries(final WddMember wddMember, final DiaryQueryCommand command) {
        Objects.requireNonNull(wddMember);
        Objects.requireNonNull(command);

        return diaryFindPort.findAll(wddMember.getId(), command.startDate(), command.endDate());
    }

    @Override
    public Diary queryDiary(final WddMember wddMember, final Long id) {
        Objects.requireNonNull(wddMember);
        Objects.requireNonNull(id);

        return diaryFindPort.findDiary(id, wddMember.getId())
            .orElseThrow(() -> new WddException(DiaryException.NONE_CONTENTS));
    }
}
