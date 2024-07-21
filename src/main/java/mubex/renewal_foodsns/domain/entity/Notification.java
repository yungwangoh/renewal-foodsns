package mubex.renewal_foodsns.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mubex.renewal_foodsns.domain.type.NotificationType;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Notification extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "text", nullable = false)
    private String text;

    @Enumerated
    @Column(name = "type", nullable = false)
    private NotificationType type;

    @Column(name = "uri")
    private String uri;

    @Column(name = "in_deleted", nullable = false)
    private boolean inDeleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "member_id",
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
    )
    private Member member;

    @Builder
    private Notification(String text, NotificationType type, String uri, boolean inDeleted, Member member) {
        this.text = text;
        this.type = type;
        this.uri = uri;
        this.inDeleted = inDeleted;
        this.member = member;
    }

    public static Notification create(String text, NotificationType type, String uri, boolean inDeleted,
                                      Member member) {
        return Notification.builder()
                .text(text)
                .type(type)
                .uri(uri)
                .inDeleted(inDeleted)
                .member(member)
                .build();
    }
}
