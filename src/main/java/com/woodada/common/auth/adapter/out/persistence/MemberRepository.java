package com.woodada.common.auth.adapter.out.persistence;

import com.woodada.common.auth.domain.Deleted;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberJpaEntity, Long> {

    Optional<MemberJpaEntity> findByEmail(String email);

    Optional<MemberJpaEntity> findByIdAndDeleted(Long id, Deleted deleted);
}
