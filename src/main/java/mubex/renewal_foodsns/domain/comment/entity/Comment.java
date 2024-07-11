package mubex.renewal_foodsns.domain.comment.entity;

import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mubex.renewal_foodsns.domain.BaseEntity;
import mubex.renewal_foodsns.domain.member.entity.Member;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Comment extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "text", nullable = false)
    private String text;

    @Column(name = "heart", nullable = false)
    private long heart;

    @Column(name = "report", nullable = false)
    private int report;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "member_id",
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
    )
    private Member member;

    @Builder
    private Comment(String text, long heart, int report, boolean isDeleted, Member member) {
        this.text = text;
        this.heart = heart;
        this.report = report;
        this.isDeleted = isDeleted;
        this.member = member;
    }

    public static Comment create(String text, long heart, int report, boolean isDeleted, Member member) {
        return Comment.builder()
                .text(text)
                .heart(heart)
                .isDeleted(isDeleted)
                .report(report)
                .member(member)
                .build();
    }
}
