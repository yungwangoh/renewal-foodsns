package mubex.renewal_foodsns.domain.post.entity;

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
import mubex.renewal_foodsns.domain.post.FoodTag;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
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

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "member_id",
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
    )
    private Member member;

    @Builder
    public Post(String title, String text, long heart, int report, FoodTag foodTag, long views, boolean isDeleted,
                Member member) {
        this.title = title;
        this.text = text;
        this.heart = heart;
        this.report = report;
        this.foodTag = foodTag;
        this.views = views;
        this.isDeleted = isDeleted;
        this.member = member;
    }

    public static Post create(String title, String text, long heart, int report, FoodTag foodTag, long views, boolean isDeleted, Member member) {
        return Post.builder()
                .title(title)
                .text(text)
                .heart(heart)
                .report(report)
                .foodTag(foodTag)
                .views(views)
                .isDeleted(isDeleted)
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
        this.isDeleted = true;
    }
}
