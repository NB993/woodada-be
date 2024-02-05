package com.woodada.common.auth.adapter.out.persistence;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberJpaEntity, Long> {

    Optional<MemberJpaEntity> findByEmail(String email);
}
