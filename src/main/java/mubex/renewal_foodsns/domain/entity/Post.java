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
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mubex.renewal_foodsns.domain.type.FoodTag;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(
        name = "post",
        indexes = {
                @Index(name = "title_idx", columnList = "title", unique = true),
                @Index(name = "food_tag_idx", columnList = "food_tag")
        }
)
public class Post extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Lob
    @Column(name = "text", nullable = false)
    private String text;

    // 좋아요 수
    @Column(name = "heart", nullable = false)
    private long heart;

    // 신고 수
    @Column(name = "report", nullable = false)
    private int report;

    @Column(name = "food_tag", nullable = false)
    private FoodTag foodTag;

    @Column(name = "views", nullable = false)
    private long views;

    @Column(name = "in_deleted", nullable = false)
    private boolean inDeleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "member_id",
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
    )
    private Member member;

    @Builder
    public Post(String title, String text, long heart, int report, FoodTag foodTag, long views, boolean inDeleted,
                Member member) {
        this.title = title;
        this.text = text;
        this.heart = heart;
        this.report = report;
        this.foodTag = foodTag;
        this.views = views;
        this.inDeleted = inDeleted;
        this.member = member;
    }

    public static Post create(String title, String text, long heart, int report, FoodTag foodTag, long views, boolean inDeleted, Member member) {
        return Post.builder()
                .title(title)
                .text(text)
                .heart(heart)
                .report(report)
                .foodTag(foodTag)
                .views(views)
                .inDeleted(inDeleted)
                .member(member)
                .build();
    }

    public void updateTitle(String title) {
        this.title = title;
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

    public void updateFoodTag(FoodTag foodTag) {
        this.foodTag = foodTag;
    }

    public void markAsDeleted() {
        this.inDeleted = true;
    }
}
