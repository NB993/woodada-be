package com.woodada.core.diary.application.service;

import com.woodada.core.diary.application.port.in.WriteDiaryCommand;
import com.woodada.core.diary.application.port.in.WriteDiaryUseCase;
import com.woodada.core.diary.application.port.out.SaveDiaryPort;
import com.woodada.core.diary.domain.Diary;
import org.springframework.stereotype.Service;

@Service
public class WriteDiaryService implements WriteDiaryUseCase {

    private final SaveDiaryPort saveDiaryPort;

    public WriteDiaryService(SaveDiaryPort saveDiaryPort) {
        this.saveDiaryPort = saveDiaryPort;
    }

    @Override
    public Diary writeDiary(WriteDiaryCommand command) {
        Diary diary = Diary.withoutId(command.title(), command.contents());
        return saveDiaryPort.saveDiary(diary);
    }
}
