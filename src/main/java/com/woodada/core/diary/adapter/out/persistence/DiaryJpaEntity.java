package com.woodada.core.diary.adapter.out.persistence;

import com.woodada.common.support.ModifierBaseEntity;
import com.woodada.core.diary.domain.Diary;
import com.woodada.support.Deleted;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Objects;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "deleted", nullable = false, length = 5)
    private Deleted deleted;

    private DiaryJpaEntity(final Long id, final String title, final String contents, final Deleted deleted) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.deleted = deleted;
    }

    /**
     * DomainEntity to JpaEntity 매핑
     *
     * @param diary 일기 도메인 엔티티
     * @throws NullPointerException diary에 null이 입력된 경우
     * @return 일기 JPA 엔티티
     */
    public static DiaryJpaEntity from(final Diary diary) {
        Objects.requireNonNull(diary);

        return new DiaryJpaEntity(diary.getId(), diary.getTitle(), diary.getContents(), Deleted.FALSE);
    }

    /**
     * JpaEntity to DomainEntity 매핑
     *
     * @return 일기 도메인 엔티티
     */
    public Diary toDomainEntity() {
        return Diary.withId(id, title, contents, Deleted.FALSE, getCreatedBy(), getCreatedAt(), getModifiedBy(), getModifiedAt());
    }
}
