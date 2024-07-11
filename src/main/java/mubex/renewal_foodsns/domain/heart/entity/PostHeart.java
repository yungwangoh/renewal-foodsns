package mubex.renewal_foodsns.domain.heart.entity;

import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mubex.renewal_foodsns.domain.member.entity.Member;
import mubex.renewal_foodsns.domain.post.entity.Post;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PostHeart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
    private PostHeart(Post post, Member member) {
        this.post = post;
        this.member = member;
    }

    public static PostHeart create(Post post, Member member) {
        return PostHeart.builder()
                .post(post)
                .member(member)
                .build();
    }
}
