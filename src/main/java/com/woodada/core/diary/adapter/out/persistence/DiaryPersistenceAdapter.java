package com.woodada.core.diary.adapter.out.persistence;

import com.woodada.core.diary.application.port.out.DiaryFindPort;
import com.woodada.core.diary.application.port.out.DiarySavePort;
import com.woodada.core.diary.domain.Diary;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class DiaryPersistenceAdapter implements DiaryFindPort, DiarySavePort {

    private final DiaryRepository diaryRepository;

    public DiaryPersistenceAdapter(final DiaryRepository diaryRepository) {
        this.diaryRepository = diaryRepository;
    }

    @Override
    public boolean existsDiary(final Long createdBy, final LocalDate writeDate) {
        return diaryRepository.existsByCreatedByAndCreatedAtBetween(createdBy, writeDate.atStartOfDay(), writeDate.atTime(LocalTime.MAX));
    }

    @Override
    public Optional<Diary> findDiary(final Long id, final Long createdBy) {
        final Optional<DiaryJpaEntity> optionalDiary = diaryRepository.findByIdAndCreatedBy(id, createdBy);
        if (optionalDiary.isEmpty()) {
            return Optional.empty();
        }

        final DiaryJpaEntity foundDiary = optionalDiary.get();
        return Optional.of(foundDiary.toDomainEntity());
    }

    @Override
    public List<Diary> findAll(final Long createdBy, final LocalDate startDate, final LocalDate endDate) {
        final LocalDateTime startDateTime = startDate.atStartOfDay();
        final LocalDateTime endDateTime = endDate.atTime(LocalDateTime.MAX.toLocalTime());

        final List<DiaryJpaEntity> diaryJpaEntities = diaryRepository.findAllByCreatedByAndCreatedAtBetween(createdBy, startDateTime, endDateTime);
        return diaryJpaEntities.stream()
            .map(entity -> entity.toDomainEntity())
            .collect(Collectors.toList());
    }

    @Override
    public Diary saveDiary(final Diary diary) {
        final DiaryJpaEntity diaryJpaEntity = DiaryJpaEntity.from(diary);
        final DiaryJpaEntity savedDiary = diaryRepository.save(diaryJpaEntity);

        return savedDiary.toDomainEntity();
    }
}
