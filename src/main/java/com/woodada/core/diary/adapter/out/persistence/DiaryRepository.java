package com.woodada.core.diary.adapter.out.persistence;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryRepository extends JpaRepository<DiaryJpaEntity, Long> {

    boolean existsByCreatedByAndCreatedAtBetween(Long createdBy, LocalDateTime startOfDate, LocalDateTime endOfDate);

    Optional<DiaryJpaEntity> findByIdAndCreatedBy(Long id, Long createdBy);

    List<DiaryJpaEntity> findAllByCreatedByAndCreatedAtBetween(Long createdBy, LocalDateTime startDateTime, LocalDateTime endDateTime);
}
