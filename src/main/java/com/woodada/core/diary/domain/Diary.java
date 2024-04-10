package com.woodada.core.diary.domain;

import com.woodada.support.Deleted;
import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.util.ObjectUtils;

@Getter
public class Diary {

    private final Long id;
    private String title;
    private String contents;
    private Deleted deleted;
    private Long createdBy;
    private LocalDateTime createdAt;
    private Long modifiedBy;
    private LocalDateTime modifiedAt;

    private Diary(
        final Long id,
        final String title,
        final String contents,
        final Deleted deleted,
        final Long createdBy,
        final LocalDateTime createdAt,
        final Long modifiedBy,
        final LocalDateTime modifiedAt
    ) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.deleted = deleted;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.modifiedBy = modifiedBy;
        this.modifiedAt = modifiedAt;
    }

    /**
     * DB 저장 전 일기 인스턴스를 생성한다
     *
     * @param title    제목
     * @param contents 본문
     * @return 저장되지 않은 Diary
     * @throws IllegalArgumentException 생성할 일기 제목, 본문을 null 또는 한 글자 미만으로 입력한 경우
     */
    public static Diary withoutId(final String title, final String contents) {
        if (ObjectUtils.isEmpty(title) || title.length() < 1) {
            throw new IllegalArgumentException("일기 제목을 한 글자 이상 입력해 주세요.");
        }

        if (ObjectUtils.isEmpty(contents) || contents.length() < 1) {
            throw new IllegalArgumentException("일기 본문을 한 글자 이상 입력해 주세요.");
        }

        return new Diary(null, title, contents,  Deleted.FALSE, null, null, null, null);
    }

    /**
     * DB에서 조회된 일기 인스턴스를 생성한다
     *
     * @param id       diary id
     * @param title    제목
     * @param contents 본문
     * @param deleted 삭제 여부
     * @param createdBy 등록자
     * @param createdAt 등록 일시
     * @param modifiedBy 최종 수정자
     * @param modifiedAt 최종 수정 일시
     * @return 조회된 Diary
     */
    public static Diary withId(
        final Long id,
        final String title,
        final String contents,
        final Deleted deleted,
        final Long createdBy,
        final LocalDateTime createdAt,
        final Long modifiedBy,
        final LocalDateTime modifiedAt
    ) {
        return new Diary(id, title, contents, deleted, createdBy, createdAt, modifiedBy, modifiedAt);
    }

    /**
     * 일기를 수정한다
     *
     * @param title    제목
     * @param contents 본문
     * @throws IllegalArgumentException 수정할 일기 제목, 본문을 null 또는 한 글자 미만으로 입력한 경우
     */
    public void modify(final String title, final String contents) {
        if (ObjectUtils.isEmpty(title) || title.length() < 1) {
            throw new IllegalArgumentException("수정할 제목을 한 글자 이상 입력해 주세요.");
        }

        if (ObjectUtils.isEmpty(contents) || contents.length() < 1) {
            throw new IllegalArgumentException("수정할 본문을 한 글자 이상 입력해 주세요.");
        }

        this.title = title;
        this.contents = contents;
    }

    public void delete() {
        this.deleted = Deleted.TRUE;
    }
}
