package com.woodada.core.diary.adapter.out.persistence;

import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryRepository extends JpaRepository<DiaryJpaEntity, Long> {

    boolean existsByCreatedByAndCreatedAtBetween(Long createdBy, LocalDateTime startOfDate, LocalDateTime endOfDate);
}
