package com.woodada.core.diary.adapter.out.persistence;

import com.woodada.common.support.ModifierBaseEntity;
import com.woodada.core.diary.domain.Diary;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "diary")
public class DiaryJpaEntity extends ModifierBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "contents", nullable = false, length = 5000)
    private String contents;

    private DiaryJpaEntity(final Long id, final String title, final String contents) {
        this.id = id;
        this.title = title;
        this.contents = contents;
    }

    /**
     * DomainEntity to JpaEntity 매핑
     *
     * @param diary 일기 도메인 엔티티
     * @return 일기 JPA 엔티티
     */
    public static DiaryJpaEntity from(final Diary diary) {
        return new DiaryJpaEntity(diary.getId(), diary.getTitle(), diary.getContents());
    }

    /**
     * JpaEntity to DomainEntity 매핑
     *
     * @return 일기 도메인 엔티티
     */
    public Diary toDomainEntity() {
        return Diary.withId(id, title, contents, getCreatedBy(), getCreatedAt(), getModifiedBy(), getModifiedAt());
    }
}
