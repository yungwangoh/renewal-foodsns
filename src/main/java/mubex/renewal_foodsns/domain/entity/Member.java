package mubex.renewal_foodsns.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mubex.renewal_foodsns.domain.type.MemberRank;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(
        name = "member",
        indexes = {
                @Index(name = "nick_name_idx", columnList = "nick_name", unique = true),
                @Index(name = "login_idx", columnList = "email, password", unique = true),
                @Index(name = "member_rank_idx", columnList = "member_rank")
        }
)
public class Member extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nick_name", length = 20, nullable = false)
    private String nickName;

    @Column(name = "password", length = 50, nullable = false)
    private String password;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "heart", nullable = false)
    private long heart;

    @Column(name = "report", nullable = false)
    private int report;

    @Column(name = "is_black_list", nullable = false)
    private boolean isBlackList;

    @Enumerated(EnumType.STRING)
    @Column(name = "member_rank", nullable = false)
    private MemberRank memberRank;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;

    @Builder
    private Member(String nickName, String password, String email, long heart, int report, boolean isBlackList,
                  MemberRank memberRank, boolean isDeleted) {
        this.nickName = nickName;
        this.password = password;
        this.email = email;
        this.heart = heart;
        this.report = report;
        this.isBlackList = isBlackList;
        this.memberRank = memberRank;
        this.isDeleted = isDeleted;
    }

    public static Member create(
            String nickName,
            String password,
            String email,
            long heart,
            int report,
            boolean isBlackList,
            MemberRank memberRank,
            boolean isDeleted
    ) {
        return Member.builder()
                .nickName(nickName)
                .password(password)
                .email(email)
                .heart(heart)
                .report(report)
                .isBlackList(isBlackList)
                .memberRank(memberRank)
                .isDeleted(isDeleted)
                .build();
    }

    public void updateNickName(String newNickName) {
        this.nickName = newNickName;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updateMemberRank(MemberRank memberRank) {
        this.memberRank = memberRank;
    }

    public void markAsDeleted() {
        this.isDeleted = true;
    }

    public void addToBlacklist() {
        this.isBlackList = true;
    }

    public void addHeart(long heart) {
        if(heart < 0) throw new IllegalArgumentException("유효하지 않은 값입니다.");
        this.heart += heart;
    }

    public void addReport(int report) {
        if(report < 0) throw new IllegalArgumentException("유효하지 않은 값입니다.");
        this.report += report;
    }
}
