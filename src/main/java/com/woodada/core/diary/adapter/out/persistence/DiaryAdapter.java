package com.woodada.core.diary.adapter.out.persistence;

import com.woodada.core.diary.application.port.out.FindDiaryPort;
import com.woodada.core.diary.application.port.out.SaveDiaryPort;
import com.woodada.core.diary.domain.Diary;
import java.time.LocalDate;
import org.springframework.stereotype.Component;

@Component
public class DiaryAdapter implements FindDiaryPort, SaveDiaryPort {

    @Override
    public boolean existsDiaryByCreatedByAndWriteDate(Long createdBy, LocalDate writeDate) {
        return false;
    }

    @Override
    public Diary saveDiary(Diary diary) {
        return null;
    }
}
