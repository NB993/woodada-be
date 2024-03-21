package com.woodada.core.diary.adapter.out.persistence;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryRepository extends JpaRepository<DiaryJpaEntity, Long> {

    Optional<DiaryJpaEntity> findByMemberId(Long memberId);
}
