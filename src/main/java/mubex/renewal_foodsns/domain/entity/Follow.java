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
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "follow",
        indexes = {
                @Index(name = "follower_idx", columnList = "follower_id"),
                @Index(name = "followee_idx", columnList = "followee_id")
        }
)
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "follower_id",
            nullable = false,
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
    )
    private Member follower;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "followee_id",
            nullable = false,
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
    )
    private Member followee;

    @Column(name = "in_deleted", nullable = false)
    private boolean inDeleted;

    @Builder
    private Follow(final Member follower, final Member followee, final boolean inDeleted) {
        this.follower = follower;
        this.followee = followee;
        this.inDeleted = inDeleted;
    }

    public static Follow create(final Member follower, final Member followee, final boolean inDeleted) {
        return Follow.builder()
                .follower(follower)
                .followee(followee)
                .inDeleted(inDeleted)
                .build();
    }

    public void delete() {
        this.inDeleted = true;
    }

    public void update(final Member from, final Member to) {
        this.follower = from;
        this.followee = to;
    }
}
