package com.woodada.core.diary.adapter.out.persistence;

import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryRepository extends JpaRepository<DiaryJpaEntity, Long> {

    boolean existsByCreatedByAndCreatedAtBetween(Long memberId, LocalDateTime startOfDate, LocalDateTime endOfDate);
}
