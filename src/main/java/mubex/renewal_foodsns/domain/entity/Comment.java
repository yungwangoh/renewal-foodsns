package mubex.renewal_foodsns.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(
        name = "comment",
        indexes = {
                @Index(name = "member_comment_idx", columnList = "member_id, id"),
                @Index(name = "post_comment_idx", columnList = "post_id, id")
        }
)
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
            name = "post_id",
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
    )
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "member_id",
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
    )
    private Member member;

    @Builder
    private Comment(String text, long heart, int report, boolean isDeleted, Post post, Member member) {
        this.text = text;
        this.heart = heart;
        this.report = report;
        this.isDeleted = isDeleted;
        this.post = post;
        this.member = member;
    }

    public static Comment create(String text, long heart, int report, boolean isDeleted, Post post, Member member) {
        return Comment.builder()
                .text(text)
                .heart(heart)
                .isDeleted(isDeleted)
                .report(report)
                .post(post)
                .member(member)
                .build();
    }

    public void markAsDeleted() {
        isDeleted = true;
    }

    public void updateText(String text) {
        this.text = text;
    }

    public void addHeart() {
        this.heart++;
    }

    public void addReport() {
        this.report++;
    }

    public boolean isCommentAuthor(Long memberId) {
        return Objects.equals(member.getId(), memberId);
    }
}
