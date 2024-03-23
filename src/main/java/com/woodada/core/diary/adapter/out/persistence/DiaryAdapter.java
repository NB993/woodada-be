package com.woodada.core.diary.adapter.out.persistence;

import com.woodada.core.diary.application.port.out.SaveDiaryPort;
import com.woodada.core.diary.domain.Diary;
import org.springframework.stereotype.Component;

@Component
public class DiaryAdapter implements SaveDiaryPort {

    @Override
    public Diary saveDiary(Diary diary) {
        return null;
    }
}
