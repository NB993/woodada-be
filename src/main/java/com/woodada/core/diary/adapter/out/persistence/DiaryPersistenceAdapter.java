package com.woodada.core.diary.adapter.out.persistence;

import com.woodada.core.diary.application.port.out.FindDiaryPort;
import com.woodada.core.diary.application.port.out.SaveDiaryPort;
import com.woodada.core.diary.domain.Diary;
import java.time.LocalDate;
import org.springframework.stereotype.Component;

@Component
public class DiaryPersistenceAdapter implements FindDiaryPort, SaveDiaryPort {

    private final DiaryRepository diaryRepository;

    public DiaryPersistenceAdapter(final DiaryRepository diaryRepository) {
        this.diaryRepository = diaryRepository;
    }

    @Override
    public boolean existsDiaryByCreatedByAndWriteDate(Long createdBy, LocalDate writeDate) {
        return false;
    }

    @Override
    public Diary saveDiary(final Diary diary) {
        final DiaryJpaEntity diaryJpaEntity = DiaryJpaEntity.from(diary);
        final DiaryJpaEntity savedDiary = diaryRepository.save(diaryJpaEntity);

        return savedDiary.toDomainEntity();
    }
}
