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
                @Index(name = "from_idx", columnList = "from"),
                @Index(name = "to_idx", columnList = "to")
        }
)
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "from",
            nullable = false,
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
    )
    private Member from;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "to",
            nullable = false,
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
    )
    private Member to;

    @Column(name = "in_deleted", nullable = false)
    private boolean inDeleted;

    @Builder
    private Follow(final Member from, final Member to, final boolean inDeleted) {
        this.from = from;
        this.to = to;
        this.inDeleted = inDeleted;
    }

    public static Follow create(final Member from, final Member to, final boolean inDeleted) {
        return Follow.builder()
                .from(from)
                .to(to)
                .inDeleted(inDeleted)
                .build();
    }

    public void delete() {
        this.inDeleted = true;
    }

    public void update(final Member from, final Member to) {
        this.from = from;
        this.to = to;
    }
}
